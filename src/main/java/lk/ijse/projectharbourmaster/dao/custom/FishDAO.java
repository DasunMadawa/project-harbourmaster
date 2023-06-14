package lk.ijse.projectharbourmaster.dao.custom;

import lk.ijse.projectharbourmaster.dao.CrudDAO;
import lk.ijse.projectharbourmaster.dto.StockUpdateDTO;
import lk.ijse.projectharbourmaster.entity.Fish;

import java.sql.SQLException;
import java.util.List;

public interface FishDAO extends CrudDAO<Fish> {
    public List<Fish> getAllOrderBy(String order) throws SQLException;
    public boolean updateFishStock(double newStock , String fishId) throws SQLException;

}
