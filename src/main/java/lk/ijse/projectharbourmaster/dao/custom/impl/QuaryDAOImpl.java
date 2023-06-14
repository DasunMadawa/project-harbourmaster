package lk.ijse.projectharbourmaster.dao.custom.impl;

import lk.ijse.projectharbourmaster.dao.custom.QuaryDAO;
import lk.ijse.projectharbourmaster.dto.tm.CrewTM;
import lk.ijse.projectharbourmaster.entity.CustomEntity;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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

    @Override
    public List<CustomEntity> getAllStockFishRecords() throws SQLException {
        String sql = "SELECT stock_fish.stockId , stock_fish.fishId , stock_fish.weight , stock_fish.date , stock_fish.unitPriceBought , stock_fish.addOrRemove , fish.name FROM stock_fish INNER JOIN fish ON stock_fish.fishId = fish.fishId";

        ResultSet rs = CrudUtil.execute(sql);

        List<CustomEntity> stockRecordsTMS = new ArrayList<>();

        while (rs.next()) {
            stockRecordsTMS.add(
                    new CustomEntity(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getDouble(3),
                            rs.getString(4),
                            rs.getDouble(5),
                            rs.getString(6),
                            rs.getString(7)
                    )

            );
        }

        return stockRecordsTMS;

    }

    @Override
    public List<String> getAllCrewInSea() throws SQLException {
        String sql = "SELECT turn_crew.turnId , turn_crew.nic , turn.outDate , turn.inDate FROM turn_crew INNER JOIN turn ON turn_crew.turnId = turn.turnId WHERE turn.inDate IS NULL ORDER BY turn_crew.nic";

        ResultSet rs = CrudUtil.execute(sql);
        List<String> crewIdList = new ArrayList<>();

        while (rs.next()){
            crewIdList.add(rs.getString(2));
        }

        return crewIdList;

    }

    @Override
    public List<CustomEntity> getAllCrewInATurn(String turnIdSearch) throws SQLException {
        String sql = "SELECT turn_crew.turnId , turn_crew.nic , crew.name , crew.address , crew.contact , crew.bod FROM turn_crew INNER JOIN crew ON turn_crew.nic = crew.nic WHERE turnId = ?";

        ResultSet rs = CrudUtil.execute(sql , turnIdSearch);

        List<CustomEntity> crewTMList = new ArrayList<>();

        while (rs.next()){
            String nic = rs.getString(2);
            String name = rs.getString(3);
            String address = rs.getString(4);
            String contact = rs.getString(5);
            String dob = rs.getString(6);

            int age = LocalDate.now().getYear() - Integer.valueOf(dob.substring(0, 4));

            crewTMList.add(new CustomEntity(nic , name , address , contact , age+""));
        }

        return crewTMList;


    }

}
