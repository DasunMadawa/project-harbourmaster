package lk.ijse.projectharbourmaster.bo.custom;

import lk.ijse.projectharbourmaster.bo.SuperBO;
import lk.ijse.projectharbourmaster.dto.FishDTO;
import lk.ijse.projectharbourmaster.dto.StockUpdateDTO;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface FishBO extends SuperBO {
    public FishDTO searchFish(String fishId) throws SQLException, IOException;

    public List<FishDTO> getAllOrderBy(String order) throws SQLException;

    public boolean addFish(FishDTO fishDTO) throws SQLException, IOException;

    public boolean updateFishPrice(FishDTO fishDTO , String id) throws SQLException, IOException;

    public boolean deleteFish(String fishId) throws SQLException;

}
