package lk.ijse.projectharbourmaster.dao.custom.impl;

import lk.ijse.projectharbourmaster.dao.custom.TurnDAO;
import lk.ijse.projectharbourmaster.dto.TurnDTO;
import lk.ijse.projectharbourmaster.dto.tm.CrewTM;
import lk.ijse.projectharbourmaster.dto.tm.TurnTM;
import lk.ijse.projectharbourmaster.entity.Turn;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TurnDAOImpl implements TurnDAO {


    @Override
    public List<Turn> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean add(Turn entity) throws SQLException, IOException {
        return false;
    }

    @Override
    public boolean update(Turn entity, String id) throws SQLException, IOException {
        return false;
    }

    @Override
    public Turn search(String turnIdSearch) throws SQLException, IOException {
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

//            List<CrewTM> allCrewInTurn = TurnCrewModel.getAllCrewInATurn(turnIdSearch);

            return new Turn(turnIdSearch , boatId , capNIC , crewCount , outDate , outTime , inDate , inTime );

        }

        return null;

    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public String getLastTurnID() throws SQLException {
        String sql = "SELECT turnId FROM turn ORDER BY turnId DESC LIMIT 1";

        ResultSet rs = CrudUtil.execute(sql);

        if (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }

    @Override
    public List<String> getAllBoatsInSea() throws SQLException {
        String sql = "SELECT * FROM turn WHERE inDate IS NULL";

        ResultSet rs = CrudUtil.execute(sql);
        List<String> boatIdList = new ArrayList<>();

        while (rs.next()){
            boatIdList.add(rs.getString(2));
        }

        return boatIdList;

    }

    @Override
    public boolean registerTurn(Turn turn) throws SQLException {
        String sql = "INSERT INTO turn VALUES (? , ? , ? , ? , ? , ? , ? , ?)";

        boolean isInserted = CrudUtil.execute(sql ,
                turn.getTurnId() ,
                turn.getBoatId() ,
                turn.getCapNIC() ,
                turn.getCrewCount() ,
                turn.getOutDate() ,
                turn.getOutTime() ,
                null ,
                null
        );

        return isInserted;

    }

    @Override
    public boolean endTurnDetailsUpdate(Turn turn) throws SQLException {
        String sql = "UPDATE turn SET inDate = ? , inTime = ? WHERE turnId = ?";

        return CrudUtil.execute( sql , turn.getInDate() , turn.getInTime() , turn.getTurnId() );

    }

    @Override
    public List<Turn> getAllCompletedTurns() throws SQLException {
        String sql = "SELECT * FROM turn WHERE inDate IS NOT NULL";

        ResultSet rs = CrudUtil.execute(sql);

        List<Turn> turn = new ArrayList<>();

        while (rs.next()){
            turn.add(new Turn(rs.getString(1) ,
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
        return turn;

    }

    @Override
    public List<Turn> getAllInCompletedTurns() throws SQLException {
        String sql = "SELECT * FROM turn WHERE inDate IS NULL";

        ResultSet rs = CrudUtil.execute(sql);

        List<Turn> turn = new ArrayList<>();

        while (rs.next()){
            turn.add(new Turn(rs.getString(1) ,
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
        return turn;

    }

    @Override
    public List<Turn> getAllInCompletedTurnsInDanger(List<Turn> allInCompletedTurns) throws SQLException {

        String sql = "SELECT turnId , outDate , DATEDIFF(CURDATE() , outDate) AS daysCount FROM turn WHERE turnId = ?";

        List<Turn> warningTurns = new ArrayList<>();

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
