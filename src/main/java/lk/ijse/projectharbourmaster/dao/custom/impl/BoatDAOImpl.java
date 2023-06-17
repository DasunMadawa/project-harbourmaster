package lk.ijse.projectharbourmaster.dao.custom.impl;

import lk.ijse.projectharbourmaster.dao.custom.BoatDAO;
import lk.ijse.projectharbourmaster.dto.BoatDTO;
import lk.ijse.projectharbourmaster.dto.tm.BoatTM;
import lk.ijse.projectharbourmaster.entity.Boat;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoatDAOImpl implements BoatDAO {


    @Override
    public List<Boat> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean add(Boat boat) throws SQLException {
        String sql = ( "INSERT INTO boat VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? )" );

        if (boat.getBoatOwnerEmail().length() == 0){
            boat.setBoatOwnerEmail(null);
        }

        return CrudUtil.execute(sql , boat.getBoatId() , boat.getBoatOwner() , boat.getBoatName() , boat.getBoatType() , boat.getNoCrew() , boat.getFuelTankCap() , boat.getFreshWaterCap() , boat.getMaxWeight() , boat.getBoatOwnerEmail() );

    }

    @Override
    public boolean update(Boat boat, String boatId) throws SQLException {
        String sql = "UPDATE boat set boatId = ? , boatOwner = ? , boatName = ? , boatType = ? , noCrew = ? , fuelTankCap = ? , freshWaterCap = ? , maxWeight = ? , boatOwnerEmail = ? WHERE boatId = ?";

        if (boat.getBoatOwnerEmail() == null || boat.getBoatOwnerEmail().length() == 0){
            boat.setBoatOwnerEmail(null);
        }

        return CrudUtil.execute(sql , boat.getBoatId() , boat.getBoatOwner() , boat.getBoatName() , boat.getBoatType() , boat.getNoCrew() , boat.getFuelTankCap() , boat.getFreshWaterCap() , boat.getMaxWeight() , boat.getBoatOwnerEmail() , boatId );

    }

    @Override
    public Boat search(String boatId) throws SQLException {
        String sql = "SELECT * FROM boat WHERE boatId = ? ";

        ResultSet rs = CrudUtil.execute(sql, boatId);

        if (rs.next()) {
            String boatOwner = rs.getString(2);
            String boatName = rs.getString(3);
            String boatType = rs.getString(4);
            int noCrew = rs.getInt(5);
            double fuelCap = rs.getDouble(6);
            double waterCap = rs.getDouble(7);
            double maxWeight = rs.getDouble(8);
            String email = rs.getString(9);

            return new Boat(boatId, boatOwner, boatName, boatType, noCrew, fuelCap, waterCap, maxWeight, email);

        }
        return null;

    }

    @Override
    public boolean delete(String boatId) throws SQLException {
        String sql = "DELETE FROM boat WHERE boatId = ?";

        return CrudUtil.execute(sql, boatId);

    }

    @Override
    public List<String> getAllIds() throws SQLException {
        String sql = "SELECT * FROM boat";

        ResultSet rs = CrudUtil.execute(sql);

        List<String> boatIdList = new ArrayList<>();

        while (rs.next()) {
            boatIdList.add(rs.getString(1));
        }

        return boatIdList;

    }

    @Override
    public List<String> getAllEmails() throws SQLException {
        String sql = "SELECT boatOwnerEmail FROM boat WHERE boatOwnerEmail IS NOT NULL;";

        ResultSet rs = CrudUtil.execute(sql);

        List<String> emails = new ArrayList<>();

        while (rs.next()) {
            emails.add(rs.getString(1));
        }

        return emails;

    }


}
