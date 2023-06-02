package lk.ijse.projectharbourmaster.model;

import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.TurnDTO;
import lk.ijse.projectharbourmaster.dto.tm.CrewTM;
import lk.ijse.projectharbourmaster.dto.tm.TurnFishTM;
import lk.ijse.projectharbourmaster.dto.tm.TurnTM;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TurnModel {
    public static String generateNextTurnId() throws SQLException {
        String sql = "SELECT turnId FROM turn ORDER BY turnId DESC LIMIT 1";

        ResultSet rs = CrudUtil.execute(sql);

        if (rs.next()) {
            return splitTurnId(rs.getString(1));
        }
        return splitTurnId(null);

    }

    public static String splitTurnId(String currentTurnId) {
        if(currentTurnId != null) {
            String[] strings = currentTurnId.split("T");
            int id = Integer.parseInt(strings[1]);
            id++;

            String newId = id + "";

            if (newId.length() == 1){
                return "T00"+ newId;
            }else if (newId.length() == 2){
                return "T0" + newId;
            }else {
                return "T" + newId;
            }

        }
        return "T001";
    }

    public static List<String> getAllBoatsInSea() throws SQLException {
        String sql = "SELECT * FROM TURN WHERE inDate IS NULL";

        ResultSet rs = CrudUtil.execute(sql);
        List<String> boatIdList = new ArrayList<>();

        while (rs.next()){
            boatIdList.add(rs.getString(2));
        }

        return boatIdList;

    }

    public static boolean registerTurn(TurnDTO turnDTO) throws SQLException {
        String sql = "INSERT INTO turn VALUES (? , ? , ? , ? , ? , ? , ? , ?)";

        boolean isInserted = CrudUtil.execute(sql ,
                turnDTO.getTurnId() ,
                turnDTO.getBoatId() ,
                turnDTO.getCapNIC() ,
                turnDTO.getCrewCount() ,
                turnDTO.getOutDate() ,
                turnDTO.getOutTime() ,
                null ,
                null
        );

        return isInserted;

    }

    public static TurnDTO searchTurn(String turnIdSearch) throws SQLException, IOException {
        String sql = "SELECT * FROM turn WHERE turnId = ? ";
        ResultSet rs = CrudUtil.execute(sql, turnIdSearch );

        if (rs.next()){
            String boatId = rs.getString(2);
            String capNIC = rs.getString(3);
            int crewCount = rs.getInt(4);
            String outDate = rs.getString(5);
            String outTime = rs.getString(6);
            String inDate = rs.getString(7);
            String inTime = rs.getString(8);

            List<CrewTM> allCrewInTurn = TurnCrewModel.getAllCrewInATurn(turnIdSearch);

            return new TurnDTO(turnIdSearch , boatId , capNIC , crewCount , outDate , outTime , inDate , inTime , allCrewInTurn);

        }

        return null;

    }

    public static boolean endTurn(TurnDTO turnDTO, List<TurnFishTM> turnFishTMList ) throws SQLException {

        Connection con = null;

        try{
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean isInserted = TurnFishModel.insertTurnFishDetails( turnFishTMList , turnDTO.getTurnId() , turnDTO.getInDate() );

            if (isInserted){
                boolean isUpdated = TurnModel.endTurnDetailsUpdate(turnDTO);

                if (isUpdated){
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

    private static boolean endTurnDetailsUpdate(TurnDTO turnDTO) throws SQLException {
        String sql = "UPDATE turn SET inDate = ? , inTime = ? WHERE turnId = ?";

        return CrudUtil.execute( sql , turnDTO.getInDate() , turnDTO.getInTime() , turnDTO.getTurnId() );

    }

    public static List<TurnTM> getAllCompletedTurns() throws SQLException {
        String sql = "SELECT * FROM turn WHERE inDate IS NOT NULL";

        ResultSet rs = CrudUtil.execute(sql);

        List<TurnTM> turnTMS = new ArrayList<>();

        while (rs.next()){
            turnTMS.add(new TurnTM(rs.getString(1) ,
                    rs.getString(2) ,
                    rs.getString(3) ,
                    rs.getInt(4) ,
                    rs.getString(5) ,
                    rs.getString(6) ,
                    rs.getString(7) ,
                    rs.getString(8)
                    )
            );

        }
        return turnTMS;

    }

    public static List<TurnTM> getAllInCompletedTurns() throws SQLException {
        String sql = "SELECT * FROM turn WHERE inDate IS NULL";

        ResultSet rs = CrudUtil.execute(sql);

        List<TurnTM> turnTMS = new ArrayList<>();

        while (rs.next()){
            turnTMS.add(new TurnTM(rs.getString(1) ,
                            rs.getString(2) ,
                            rs.getString(3) ,
                            rs.getInt(4) ,
                            rs.getString(5) ,
                            rs.getString(6) ,
                            rs.getString(7) ,
                            rs.getString(8)
                    )
            );

        }
        return turnTMS;

    }

    public static List<TurnTM> getAllInCompletedTurnsInDanger() throws SQLException {
        List<TurnTM> allInCompletedTurns = getAllInCompletedTurns();

        String sql = "SELECT turnId , outDate , DATEDIFF(CURDATE() , outDate) AS daysCount FROM turn WHERE turnId = ?";

        List<TurnTM> warningTurns = new ArrayList<>();

        for (int i = 0; i < allInCompletedTurns.size(); i++) {
            ResultSet rs = CrudUtil.execute(sql, allInCompletedTurns.get(i).getTurnId());

            if (rs.next()){
                if (rs.getInt(3) > 30){
                    warningTurns.add(allInCompletedTurns.get(i));
                }
            }

        }

        return warningTurns;
    }
}
