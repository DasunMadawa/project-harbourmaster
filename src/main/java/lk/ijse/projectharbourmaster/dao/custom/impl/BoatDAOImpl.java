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
    public boolean add(Boat entity) throws SQLException {
        String sql = ( "INSERT INTO boat VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? )" );

        if (entity.getBoatOwnerEmail().length() == 0){
            entity.setBoatOwnerEmail(null);
        }

        return CrudUtil.execute(sql , entity.getBoatId() , entity.getBoatOwner() , entity.getBoatName() , entity.getBoatType() , entity.getNoCrew() , entity.getFuelTankCap() , entity.getFreshWaterCap() , entity.getMaxWeight() , entity.getBoatOwnerEmail() );

    }

    @Override
    public boolean update(Boat entity, String id) throws SQLException {
        String sql = "UPDATE boat set boatId = ? , boatOwner = ? , boatName = ? , boatType = ? , noCrew = ? , fuelTankCap = ? , freshWaterCap = ? , maxWeight = ? , boatOwnerEmail = ? WHERE boatId = ?";

        if (entity.getBoatOwnerEmail().length() == 0){
            entity.setBoatOwnerEmail(null);
        }

        return CrudUtil.execute(sql , entity.getBoatId() , entity.getBoatOwner() , entity.getBoatName() , entity.getBoatType() , entity.getNoCrew() , entity.getFuelTankCap() , entity.getFreshWaterCap() , entity.getMaxWeight() , entity.getBoatOwnerEmail() , id );

    }

    @Override
    public Boat search(String id) throws SQLException {
        String sql = "SELECT * FROM boat WHERE boatId = ? ";

        ResultSet rs = CrudUtil.execute(sql, id);

        if (rs.next()) {
            String boatId = rs.getString(1);
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
    public boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM boat WHERE boatId = ?";

        return CrudUtil.execute(sql, id);

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
