package lk.ijse.projectharbourmaster.model;

import lk.ijse.projectharbourmaster.dto.FishDTO;
import lk.ijse.projectharbourmaster.dto.StockUpdateDTO;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FIshModel {

    public static FishDTO searchFish(String fishId) throws SQLException {

        String sql = "SELECT * FROM fish WHERE fishId = ?";

        ResultSet rs = CrudUtil.execute(sql, fishId);

        if (rs.next()){
            return new FishDTO(
                    rs.getString(1) ,
                    rs.getString(2) ,
                    rs.getDouble(3) ,
                    rs.getDouble(4)
            );

        }

        return null;

    }

    public static List<FishDTO> getAllOrderBy(String order) throws SQLException {
        String sql = "SELECT * FROM fish " + order;

        ResultSet rs = CrudUtil.execute(sql);

        List<FishDTO> fishDTOArrayList = new ArrayList<>();

        while (rs.next()){
            fishDTOArrayList.add(new FishDTO(rs.getString(1) ,
                    rs.getString(2) ,
                    rs.getInt(3) ,
                    rs.getInt(4)
                    )
            );
        }
        return fishDTOArrayList;

    }

    public static boolean addFish(FishDTO fishDTO) throws SQLException {
        String sql = "INSERT INTO fish VALUES ( ? , ? , ? , ? )";

        return CrudUtil.execute( sql , fishDTO.getFishId() , fishDTO.getFishName() , fishDTO.getUnitPrice() , fishDTO.getStock() );

    }

    public static boolean updateFishPrice(FishDTO fishDTO) throws SQLException {
        String sql = "UPDATE fish SET name = ? , unitPrice = ? WHERE fishId = ?";

        return CrudUtil.execute(sql , fishDTO.getFishName() , fishDTO.getUnitPrice() , fishDTO. getFishId() );

    }

    public static boolean drop(FishDTO fishDTO) throws SQLException {
        String sql = "DELETE FROM fish WHERE fishId = ?";

        return CrudUtil.execute( sql , fishDTO.getFishId() );

    }

    public static boolean updateFishStock(StockUpdateDTO stockUpdateDTO) throws SQLException {
        FishDTO fishDTO = searchFish(stockUpdateDTO.getFishDTO().getFishId());
        double currentStock = fishDTO.getStock();

        double newStock = 0;
        if (stockUpdateDTO.isAdd()){
            newStock = currentStock + stockUpdateDTO.getWeight();
        }else {
            newStock = currentStock - stockUpdateDTO.getWeight();
        }


        String sql = "UPDATE fish SET stock = ? WHERE fishId = ?";

        return CrudUtil.execute(sql , newStock , stockUpdateDTO.getFishDTO().getFishId() );

    }

}
