package lk.ijse.projectharbourmaster.dao.custom;

import lk.ijse.projectharbourmaster.dao.CrudDAO;
import lk.ijse.projectharbourmaster.dto.StockUpdateDTO;
import lk.ijse.projectharbourmaster.entity.Stock;

import java.sql.SQLException;

public interface StockDAO extends CrudDAO<Stock> {
    public boolean spaceCheck(String stockId , double addingWeight) throws SQLException;
    public double getAvailableSpace(String stockId) throws SQLException;
    public boolean stockSpaceUpdate(Stock stock) throws SQLException;


}
