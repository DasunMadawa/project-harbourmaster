package lk.ijse.projectharbourmaster.dao.custom;

import lk.ijse.projectharbourmaster.dao.SuperDAO;
import lk.ijse.projectharbourmaster.entity.CustomEntity;

import java.sql.SQLException;
import java.util.List;

public interface QueryDAO extends SuperDAO {
    public List<CustomEntity> getAllBoats() throws SQLException;
    public List<CustomEntity> getAllStockFishRecords() throws SQLException;
    public List<String> getAllCrewInSea() throws SQLException;
    public List<CustomEntity> getAllCrewInATurn(String turnIdSearch) throws SQLException;


}
