package lk.ijse.projectharbourmaster.dao.custom;

import lk.ijse.projectharbourmaster.entity.CustomEntity;

import java.sql.SQLException;
import java.util.List;

public interface QuaryDAO {
    public List<CustomEntity> geAllBoats() throws SQLException;

}
