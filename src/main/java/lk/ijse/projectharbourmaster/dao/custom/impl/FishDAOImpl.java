package lk.ijse.projectharbourmaster.dao.custom.impl;

import lk.ijse.projectharbourmaster.dao.custom.FishDAO;
import lk.ijse.projectharbourmaster.dto.FishDTO;
import lk.ijse.projectharbourmaster.dto.StockUpdateDTO;
import lk.ijse.projectharbourmaster.entity.Fish;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FishDAOImpl implements FishDAO {

    @Override
    public List getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean add(Fish fish) throws SQLException, IOException {
        String sql = "INSERT INTO fish VALUES ( ? , ? , ? , ? )";

        return CrudUtil.execute( sql , fish.getFishId() , fish.getName() , fish.getUnitPrice() , fish.getStock() );

    }

    @Override
    public boolean update(Fish fish, String id) throws SQLException, IOException {
        String sql = "UPDATE fish SET name = ? , unitPrice = ? WHERE fishId = ?";

        return CrudUtil.execute(sql , fish.getName() , fish.getUnitPrice() , fish. getFishId() );

    }

    @Override
    public Fish search(String fishId) throws SQLException, IOException {
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

    @Override
    public boolean delete(String fishId) throws SQLException {
        String sql = "DELETE FROM fish WHERE fishId = ?";

        return CrudUtil.execute( sql , fishId );

    }

    @Override
    public List<Fish> getAllOrderBy(String order) throws SQLException {
        String sql = "SELECT * FROM fish " + order;

        ResultSet rs = CrudUtil.execute(sql);

        List<Fish> fishList = new ArrayList<>();

        while (rs.next()){
            fishList.add(new Fish(rs.getString(1) ,
                            rs.getString(2) ,
                            rs.getDouble(3) ,
                            rs.getDouble(4)
                    )
            );
        }
        return fishList;

    }

    @Override
    public boolean updateFishStock(double newStock , String fishId) throws SQLException {
        /*FishDTO fishDTO = searchFish(stockUpdateDTO.getFishDTO().getFishId());
        double currentStock = fishDTO.getStock();

        double newStock = 0;
        if (stockUpdateDTO.isAdd()){
            newStock = currentStock + stockUpdateDTO.getWeight();
        }else {
            newStock = currentStock - stockUpdateDTO.getWeight();
        }

*/
        String sql = "UPDATE fish SET stock = ? WHERE fishId = ?";

        return CrudUtil.execute(sql , newStock , fishId );

    }

}
