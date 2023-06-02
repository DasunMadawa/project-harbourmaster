package lk.ijse.projectharbourmaster.model;

import lk.ijse.projectharbourmaster.dto.BoatDTO;
import lk.ijse.projectharbourmaster.dto.tm.BoatTM;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoatModel {
    public static List<BoatTM> getAllWithFilter() throws SQLException {

        String sql = "SELECT * FROM boat";

        ResultSet rs = CrudUtil.execute(sql);

        List<BoatTM> boatTMList = new ArrayList<>();

        while (rs.next()){
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

            ResultSet resultSet = CrudUtil.execute(sqlForDock , boatId);

            if (resultSet.next()){
                dockId = resultSet.getString(1);
            }

            if (dockId == null){
                dockId = "Not docked";
            }

            boatTMList.add(new BoatTM(boatId , boatOwner , boatName , boatType , noCrew ,fuelCap , waterCap , maxWeight , dockId));
        }

        return boatTMList;

    }


    public static boolean insertData(BoatDTO boatDTO) throws SQLException {
        String sql = ( "INSERT INTO boat VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? )" );

        if (boatDTO.getEmail().length() == 0){
            boatDTO.setEmail(null);
        }

        return CrudUtil.execute(sql , boatDTO.getBoatId() , boatDTO.getBoatOwner() , boatDTO.getBoatName() , boatDTO.getBoatType() , boatDTO.getNoCrew() , boatDTO.getFuelCap() , boatDTO.getWaterCap() , boatDTO.getMaxWeight() , boatDTO.getEmail() );

    }

    public static BoatDTO searchBoat(String boatIdSearch) throws SQLException {
        String sql = "SELECT * FROM boat WHERE boatId = ? ";

        ResultSet rs = CrudUtil.execute(sql, boatIdSearch);

        if (rs.next()){
            String boatId = rs.getString(1);
            String boatOwner = rs.getString(2);
            String boatName = rs.getString(3);
            String boatType = rs.getString(4);
            int noCrew = rs.getInt(5);
            double fuelCap = rs.getDouble(6);
            double waterCap = rs.getDouble(7);
            double maxWeight = rs.getDouble(8);
            String email = rs.getString(9);

            return new BoatDTO( boatId , boatOwner , boatName , boatType , noCrew , fuelCap , waterCap , maxWeight , email );

        }
        return null;

    }

    public static boolean updateBoat(BoatDTO boatDTO, String boatId) throws SQLException {
        String sql = "UPDATE boat set boatId = ? , boatOwner = ? , boatName = ? , boatType = ? , noCrew = ? , fuelTankCap = ? , freshWaterCap = ? , maxWeight = ? , boatOwnerEmail = ? WHERE boatId = ?";

        if (boatDTO.getEmail() == null){
            boatDTO.setEmail(null);
        }

        return CrudUtil.execute(sql , boatDTO.getBoatId() , boatDTO.getBoatOwner() , boatDTO.getBoatName() , boatDTO.getBoatType() , boatDTO.getNoCrew() , boatDTO.getFuelCap() , boatDTO.getWaterCap() , boatDTO.getMaxWeight() , boatDTO.getEmail() , boatId );

    }

    public static List<String> selectAll() throws SQLException {
        String sql = "SELECT * FROM boat";

        ResultSet rs = CrudUtil.execute(sql);

        List<String> boatIdList = new ArrayList<>();

        while (rs.next()){
            boatIdList.add(rs.getString(1));
        }

        return boatIdList;



    }

    public static boolean dropBoat(String boatId) throws SQLException {
            String sql = "DELETE FROM boat WHERE boatId = ?";

            return CrudUtil.execute(sql , boatId);

    }

    public static List<String> getAllEmails() throws SQLException {
        String sql = "SELECT boatOwnerEmail FROM boat WHERE boatOwnerEmail IS NOT NULL;";

        ResultSet rs = CrudUtil.execute(sql);

        List<String> emails = new ArrayList<>();

        while (rs.next()){
            emails.add(rs.getString(1));
        }

        return emails;
    }

}
