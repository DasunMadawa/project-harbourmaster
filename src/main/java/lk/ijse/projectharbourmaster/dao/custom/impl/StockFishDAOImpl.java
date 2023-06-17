package lk.ijse.projectharbourmaster.dao.custom.impl;

import lk.ijse.projectharbourmaster.dao.custom.StockFishDAO;
import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.FishDTO;
import lk.ijse.projectharbourmaster.entity.Stock_fish;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class StockFishDAOImpl implements StockFishDAO {


    @Override
    public List<Stock_fish> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean add(Stock_fish stock_fish) throws SQLException, IOException {
        /*String addOrRemove = "ADD";
        if (!stock_fish.isAdd()) {
            addOrRemove = "REMOVE";
        }*/

        String sql = "INSERT INTO stock_fish VALUES ( ? , ? , ? , ? , ? , ? )";

        return CrudUtil.execute(sql,
                stock_fish.getStockId(),
                stock_fish.getFishId(),
                stock_fish.getWeight(),
                LocalDate.now(),
                stock_fish.getUnitPriceBought(),
                stock_fish.getAddOrRemove()

        );


    }

    @Override
    public boolean update(Stock_fish entity, String id) throws SQLException, IOException {
        return false;
    }

    @Override
    public Stock_fish search(String id) throws SQLException, IOException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }
}
