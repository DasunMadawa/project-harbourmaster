package lk.ijse.projectharbourmaster.model;

import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.Fish;
import lk.ijse.projectharbourmaster.dto.StockUpdate;
import lk.ijse.projectharbourmaster.dto.tm.StockRecordsTM;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StockFishModel {

    public static boolean addStock(StockUpdate stockUpdate) {
        try {
            if (stockUpdate.isAdd()) {
                if (!StockModel.spaceCheck(stockUpdate.getStockId(), stockUpdate.getWeight())) {
                    System.out.println("No Space Left");
                    return false;
                }
            } else {
                Fish fish = FIshModel.searchFish(stockUpdate.getFish().getFishId());
                double balance = fish.getStock() - stockUpdate.getWeight();

                if (balance < 0) {
                    System.out.println("No Stock Left On This Fish");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean isUpdatedStockFish = updateStockFish(stockUpdate);
            if (isUpdatedStockFish) {

                boolean isUpdatedStock = StockModel.stockSpaceUpdate(stockUpdate);
                if (isUpdatedStock) {

                    boolean isUpdatedFishstock = FIshModel.updateFishStock(stockUpdate);
                    if (isUpdatedFishstock) {
                        con.commit();
                        return true;
                    }

                }


            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;

    }

    private static boolean updateStockFish(StockUpdate stockUpdate) throws SQLException {
        String addOrRemove = "ADD";
        if (!stockUpdate.isAdd()) {
            addOrRemove = "REMOVE";
        }

        String sql = "INSERT INTO stock_fish VALUES ( ? , ? , ? , ? , ? , ? )";

        return CrudUtil.execute(sql,
                stockUpdate.getStockId(),
                stockUpdate.getFish().getFishId(),
                stockUpdate.getWeight(),
                LocalDate.now(),
                stockUpdate.getFish().getUnitPrice(),
                addOrRemove

        );

    }

    public static List<StockRecordsTM> getAll() throws SQLException {
        String sql = "SELECT stock_fish.stockId , stock_fish.fishId , stock_fish.weight , stock_fish.date , stock_fish.unitPriceBought , stock_fish.addOrRemove , fish.name FROM stock_fish INNER JOIN fish ON stock_fish.fishId = fish.fishId";

        ResultSet rs = CrudUtil.execute(sql);

        List<StockRecordsTM> stockRecordsTMS = new ArrayList<>();

        while (rs.next()) {
            stockRecordsTMS.add(
                    new StockRecordsTM(
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

}
