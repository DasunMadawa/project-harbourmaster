package lk.ijse.projectharbourmaster.model;

import lk.ijse.projectharbourmaster.dto.Fish;
import lk.ijse.projectharbourmaster.dto.StockUpdate;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FIshModel {

    public static Fish searchFish(String fishId) throws SQLException {

        String sql = "SELECT * FROM fish WHERE fishId = ?";

        ResultSet rs = CrudUtil.execute(sql, fishId);

        if (rs.next()){
            return new Fish(
                    rs.getString(1) ,
                    rs.getString(2) ,
                    rs.getDouble(3) ,
                    rs.getDouble(4)
            );

        }

        return null;

    }

    public static List<Fish> getAllOrderBy(String order) throws SQLException {
        String sql = "SELECT * FROM fish " + order;

        ResultSet rs = CrudUtil.execute(sql);

        List<Fish> fishArrayList = new ArrayList<>();

        while (rs.next()){
            fishArrayList.add(new Fish(rs.getString(1) ,
                    rs.getString(2) ,
                    rs.getInt(3) ,
                    rs.getInt(4)
                    )
            );
        }
        return fishArrayList;

    }

    public static boolean addFish(Fish fish) throws SQLException {
        String sql = "INSERT INTO fish VALUES ( ? , ? , ? , ? )";

        return CrudUtil.execute( sql , fish.getFishId() , fish.getFishName() , fish.getUnitPrice() , fish.getStock() );

    }

    public static boolean updateFishPrice(Fish fish) throws SQLException {
        String sql = "UPDATE fish SET name = ? , unitPrice = ? WHERE fishId = ?";

        return CrudUtil.execute(sql , fish.getFishName() , fish.getUnitPrice() , fish. getFishId() );

    }

    public static boolean drop(Fish fish) throws SQLException {
        String sql = "DELETE FROM fish WHERE fishId = ?";

        return CrudUtil.execute( sql , fish.getFishId() );

    }

    public static boolean updateFishStock(StockUpdate stockUpdate) throws SQLException {
        Fish fish = searchFish(stockUpdate.getFish().getFishId());
        double currentStock = fish.getStock();

        double newStock = 0;
        if (stockUpdate.isAdd()){
            newStock = currentStock + stockUpdate.getWeight();
        }else {
            newStock = currentStock - stockUpdate.getWeight();
        }


        String sql = "UPDATE fish SET stock = ? WHERE fishId = ?";

        return CrudUtil.execute(sql , newStock , stockUpdate.getFish().getFishId() );

    }

}
