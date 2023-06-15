package lk.ijse.projectharbourmaster.dao.custom.impl;

import lk.ijse.projectharbourmaster.dao.custom.DockDAO;
import lk.ijse.projectharbourmaster.dto.tm.DockTM;
import lk.ijse.projectharbourmaster.entity.Boat_dock;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DockDAOImpl implements DockDAO {

    @Override
    public List<Boat_dock> getAll() throws SQLException {
        String sql = "SELECT * FROM boat_dock WHERE outDate IS NULL";

        ResultSet rs = CrudUtil.execute(sql);

        List<Boat_dock> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new Boat_dock(rs.getString(1),
                    rs.getString(2),
                    rs.getString(3))

            );
        }

        return list;

    }

    @Override
    public boolean add(Boat_dock entity) throws SQLException, IOException {
        String sql = "INSERT INTO BOAT_dock (dockId , boatId , inDate) VALUES (? , ? , ?)";

        return CrudUtil.execute(sql, entity.getDockId(), entity.getBoatId(), LocalDate.now());

    }

    @Override
    public boolean update(Boat_dock entity, String id) throws SQLException, IOException {
        String sql = "UPDATE BOAT_dock SET outDate = ? WHERE dockId = ? && boatId = ? ";

        return CrudUtil.execute(sql, LocalDate.now(), entity.getDockId(), entity.getBoatId());

    }

    @Override
    public Boat_dock search(String id) throws SQLException, IOException {
        String sql = "SELECT * FROM boat_dock WHERE inDate IS NOT NULL && outDate IS NULL && boatId = ?";

        ResultSet rs = CrudUtil.execute(sql, id);

        if (rs.next()) {
            return new Boat_dock(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3)
            );
        }

        return null;

    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public int getAvailableCount(String dockId) throws SQLException {
        String sql = "SELECT dockId , COUNT(dockId) FROM boat_dock WHERE dockId = ? && outDate IS NULL";

        ResultSet rs = CrudUtil.execute(sql, dockId);

        if (rs.next()) {
            int count = rs.getInt(2);
            return (10 - count);
        }
        System.out.println("sql error");
        return 10;

    }

    @Override
    public List<String> getBoatIdForUndock(String dockId) throws SQLException {
        String sql = "SELECT * FROM boat_dock WHERE outDate IS NULL && dockId = ?";

        ResultSet rs = CrudUtil.execute(sql, dockId);

        List<String> boatIds = new ArrayList<>();

        while (rs.next()) {
            boatIds.add(rs.getString(2));
        }

        return boatIds;
    }

    @Override
    public boolean getBoatIdForDock(String boatId) throws SQLException {
        String sql = "SELECT * FROM boat_dock WHERE inDate IS NOT NULL && outDate IS NULL && boatId = ?";

        ResultSet rs = CrudUtil.execute(sql, boatId);

        if (rs.next()) {
            return false;
        }

        return true;
    }

}
