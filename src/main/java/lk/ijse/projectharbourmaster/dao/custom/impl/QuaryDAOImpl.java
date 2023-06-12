package lk.ijse.projectharbourmaster.dao.custom.impl;

import lk.ijse.projectharbourmaster.dao.custom.QuaryDAO;
import lk.ijse.projectharbourmaster.dto.tm.BoatTM;
import lk.ijse.projectharbourmaster.entity.CustomEntity;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuaryDAOImpl implements QuaryDAO {
    @Override
    public List<CustomEntity> geAllBoats() throws SQLException {
        String sql = "SELECT * FROM boat";

        ResultSet rs = CrudUtil.execute(sql);

        List<CustomEntity> boatList = new ArrayList<>();

        while (rs.next()) {
            String boatId = rs.getString(1);
            String boatOwner = rs.getString(2);
            String boatName = rs.getString(3);
            String boatType = rs.getString(4);
            int noCrew = rs.getInt(5);
            double fuelCap = rs.getDouble(6);
            double waterCap = rs.getDouble(7);
            double maxWeight = rs.getDouble(8);

            String dockId = null;

            String sqlForDock = "SELECT * FROM boat_dock WHERE boatId = ? && outDate IS NULL";

            ResultSet resultSet = CrudUtil.execute(sqlForDock, boatId);

            if (resultSet.next()) {
                dockId = resultSet.getString(1);
            }

            if (dockId == null) {
                dockId = "Not docked";
            }

            boatList.add(new CustomEntity(boatId, boatOwner, boatName, boatType, noCrew, fuelCap, waterCap, maxWeight, dockId));

        }

        return boatList;

    }

}
