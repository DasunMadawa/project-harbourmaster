package lk.ijse.projectharbourmaster.model;

import lk.ijse.projectharbourmaster.dto.tm.BoatTM;
import lk.ijse.projectharbourmaster.dto.tm.DockTM;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DockModel {
    public static int getAvailableCount(String dockId) throws SQLException {
        String sql = "SELECT dockId , COUNT(dockId) FROM boat_dock WHERE dockId = ? && outDate IS NULL";

        ResultSet rs = CrudUtil.execute(sql , dockId);

        if (rs.next()){
            int count = rs.getInt(2);
            return (10-count);
        }
        System.out.println("sql error");
        return 10;


    }

    public static List<String> getBoatIdForUndock(String dockId) throws SQLException {
        String sql = "SELECT * FROM boat_dock WHERE outDate IS NULL && dockId = ?";

        ResultSet rs = CrudUtil.execute(sql , dockId);

        List<String> boatIds = new ArrayList<>();

        while (rs.next()){
            boatIds.add(rs.getString(2));
        }

        return boatIds;

    }

    public static List<DockTM> getAll(String... args) throws SQLException {
        String order = "";
        if (args.length == 1){
            order = " ORDER BY " + args[0];
        }

        String sql = "SELECT * FROM boat_dock WHERE outDate IS NULL" + order;

        ResultSet rs = CrudUtil.execute(sql);

        List<DockTM> table = new ArrayList<>();

        while (rs.next()){
            table.add(new DockTM(rs.getString(1) ,
                    rs.getString(2) ,
                    rs.getString(3))

            );
        }

        return table;

    }

    public static boolean getBoatIdForDock(String boatId) throws SQLException {
        String sql = "SELECT * FROM boat_dock WHERE inDate IS NOT NULL && outDate IS NULL && boatId = ?";

        ResultSet rs = CrudUtil.execute(sql , boatId);

        if (rs.next()){
            return false;
        }

        return true;

    }

    public static DockTM searchBoat(String boatId) throws SQLException {
        String sql = "SELECT * FROM boat_dock WHERE inDate IS NOT NULL && outDate IS NULL && boatId = ?";

        ResultSet rs = CrudUtil.execute(sql , boatId);

        if (rs.next()){
            return new DockTM(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3)
            );
        }

        return null;

    }

    public static boolean dockBoats(String dockId, String boatId) throws SQLException {
        String sql = "INSERT INTO BOAT_dock (dockId , boatId , inDate) VALUES (? , ? , ?)";

        return CrudUtil.execute(sql , dockId , boatId , LocalDate.now());

    }

    public static boolean undockBoats(String dockId, String boatId) throws SQLException {
        String sql = "UPDATE BOAT_dock SET outDate = ? WHERE dockId = ? && boatId = ? ";

        return CrudUtil.execute(sql , LocalDate.now() , dockId , boatId);

    }





}
