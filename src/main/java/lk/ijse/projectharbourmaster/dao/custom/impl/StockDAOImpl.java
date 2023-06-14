package lk.ijse.projectharbourmaster.dao.custom.impl;

import lk.ijse.projectharbourmaster.dao.custom.StockDAO;
import lk.ijse.projectharbourmaster.dto.StockUpdateDTO;
import lk.ijse.projectharbourmaster.entity.Stock;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StockDAOImpl implements StockDAO {


    @Override
    public List<Stock> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean add(Stock entity) throws SQLException, IOException {
        return false;
    }

    @Override
    public boolean update(Stock entity, String id) throws SQLException, IOException {
        return false;
    }

    @Override
    public Stock search(String id) throws SQLException, IOException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean spaceCheck(String stockId, double addingWeight) throws SQLException {
        String sql = "SELECT * FROM stock WHERE stockId = ?";

        ResultSet rs = CrudUtil.execute(sql, stockId);
        if (rs.next()){
            double availableCap = rs.getDouble(3);

            double balance = availableCap - addingWeight;

            if (balance < 0){
                return false;
            }
            return true;
        }

        return false;

    }

    @Override
    public double getAvailableSpace(String stockId) throws SQLException {
        String sql = "SELECT * FROM stock WHERE stockId = ?";

        ResultSet rs = CrudUtil.execute(sql, stockId);
        if (rs.next()){
            return rs.getDouble(3);
        }

        return -1;

    }

    @Override
    public boolean stockSpaceUpdate(Stock stock , boolean isAdd) throws SQLException {
        double availableSpace = getAvailableSpace(stock.getStockId());
        double balance = 0;

        if ( isAdd ){
            balance = availableSpace - stock.getAvailableCapacity();
        }else {
            balance = availableSpace + stock.getAvailableCapacity();
        }


        String sql = "UPDATE stock SET availableCapacity = ? WHERE stockId = ?";

        return CrudUtil.execute(sql, balance , stock.getStockId());

    }

}
