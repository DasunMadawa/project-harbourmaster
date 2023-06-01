package lk.ijse.projectharbourmaster.model;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.Crew;
import lk.ijse.projectharbourmaster.dto.Turn;
import lk.ijse.projectharbourmaster.dto.tm.CrewTM;
import lk.ijse.projectharbourmaster.dto.tm.TurnFishTM;
import lk.ijse.projectharbourmaster.dto.tm.TurnTM;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
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

    public static boolean registerTurn(Turn turn) throws SQLException {
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

    public static Turn searchTurn(String turnIdSearch) throws SQLException, IOException {
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

            return new Turn(turnIdSearch , boatId , capNIC , crewCount , outDate , outTime , inDate , inTime , allCrewInTurn);

        }

        return null;

    }

    public static boolean endTurn(Turn turn , List<TurnFishTM> turnFishTMList ) throws SQLException {

        Connection con = null;

        try{
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean isInserted = TurnFishModel.insertTurnFishDetails( turnFishTMList , turn.getTurnId() , turn.getInDate() );

            if (isInserted){
                boolean isUpdated = TurnModel.endTurnDetailsUpdate(turn);

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

    private static boolean endTurnDetailsUpdate(Turn turn) throws SQLException {
        String sql = "UPDATE turn SET inDate = ? , inTime = ? WHERE turnId = ?";

        return CrudUtil.execute( sql , turn.getInDate() , turn.getInTime() , turn.getTurnId() );

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
