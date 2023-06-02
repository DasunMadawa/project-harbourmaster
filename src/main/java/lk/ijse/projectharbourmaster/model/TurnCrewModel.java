package lk.ijse.projectharbourmaster.model;

import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.TurnDTO;
import lk.ijse.projectharbourmaster.dto.tm.CrewTM;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TurnCrewModel {
    public static List<String> getAllCrewInSea() throws SQLException {
        String sql = "SELECT turn_crew.turnId , turn_crew.nic , turn.outDate , turn.inDate FROM turn_crew INNER JOIN turn ON turn_crew.turnId = turn.turnId WHERE turn.inDate IS NULL ORDER BY turn_crew.nic";

        ResultSet rs = CrudUtil.execute(sql);
        List<String> crewIdList = new ArrayList<>();

        while (rs.next()){
            crewIdList.add(rs.getString(2));
        }

        return crewIdList;

    }

    public static boolean placeCrewInTurn(List <CrewTM> crewTMList , String capNIC , String turnId) throws SQLException {
        String sql = "INSERT INTO turn_crew VALUES(? , ?)";

        for (int i = 0; i < crewTMList.size(); i++){
            boolean isInserted = CrudUtil.execute(sql , crewTMList.get(i).getNic() ,turnId);

            if (!isInserted){
                return false;
            }

            if (i == (crewTMList.size()-1)){
                return CrudUtil.execute(sql , capNIC , turnId);

            }
        }

        return false;
    }


    public static boolean startTurn(TurnDTO turnDTO) throws SQLException {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean isUpdatedTurn = TurnModel.registerTurn(turnDTO);

            if (isUpdatedTurn){
                boolean isUpdatedTurnCrew = TurnCrewModel.placeCrewInTurn(turnDTO.getCrewTM() , turnDTO.getCapNIC() , turnDTO.getTurnId());

                if (isUpdatedTurnCrew) {
                    con.commit();
                    return true;
                }
            }
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            con.rollback();
            return false;

        }finally {
            con.rollback();
            con.setAutoCommit(true);

        }

    }

    public static List<CrewTM> getAllCrewInATurn(String turnIdSearch) throws SQLException {
        String sql = "SELECT turn_crew.turnId , turn_crew.nic , crew.name , crew.address , crew.contact , crew.bod FROM turn_crew INNER JOIN crew ON turn_crew.nic = crew.nic WHERE turnId = ?";

        ResultSet rs = CrudUtil.execute(sql , turnIdSearch);

        List<CrewTM> crewTMList = new ArrayList<>();

        while (rs.next()){
            String nic = rs.getString(2);
            String name = rs.getString(3);
            String address = rs.getString(4);
            String contact = rs.getString(5);
            String dob = rs.getString(6);

            int age = LocalDate.now().getYear() - Integer.valueOf(dob.substring(0, 4));

            crewTMList.add(new CrewTM(nic , name , address , contact , age+""));
        }

        return crewTMList;

    }

}
