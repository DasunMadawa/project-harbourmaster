package lk.ijse.projectharbourmaster.bo.custom;

import lk.ijse.projectharbourmaster.bo.SuperBO;
import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.CustomDTO;
import lk.ijse.projectharbourmaster.dto.FishDTO;
import lk.ijse.projectharbourmaster.dto.StockUpdateDTO;
import lk.ijse.projectharbourmaster.dto.tm.StockRecordsTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface StockBO extends SuperBO {
    public List<FishDTO> getAllOrderBy(String order) throws SQLException;
    public List<CustomDTO> getAllStockRecords() throws SQLException;
    public double getAvailableSpace(String stockId) throws SQLException;
    public FishDTO searchFish(String fishId) throws SQLException, IOException;
    public boolean addStock(StockUpdateDTO stockUpdateDTO);

}
