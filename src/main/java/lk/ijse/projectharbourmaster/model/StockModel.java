package lk.ijse.projectharbourmaster.model;

import lk.ijse.projectharbourmaster.dto.StockUpdateDTO;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockModel {

    public static boolean spaceCheck(String stockId , double addingWeight) throws SQLException {
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

    public static double getAvailableSpace(String stockId) throws SQLException {
        String sql = "SELECT * FROM stock WHERE stockId = ?";

        ResultSet rs = CrudUtil.execute(sql, stockId);
        if (rs.next()){
            return rs.getDouble(3);
        }

        return -1;
    }

    public static boolean stockSpaceUpdate(StockUpdateDTO stockUpdateDTO) throws SQLException {
        double availableSpace = getAvailableSpace(stockUpdateDTO.getStockId());
        double balance = 0;

        if (stockUpdateDTO.isAdd()){
            balance = availableSpace - stockUpdateDTO.getWeight();
        }else {
            balance = availableSpace + stockUpdateDTO.getWeight();
        }


        String sql = "UPDATE stock SET availableCapacity = ? WHERE stockId = ?";

        return CrudUtil.execute(sql, balance , stockUpdateDTO.getStockId());

    }

}
