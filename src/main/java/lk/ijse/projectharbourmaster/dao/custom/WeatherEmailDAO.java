package lk.ijse.projectharbourmaster.dao.custom;

import lk.ijse.projectharbourmaster.dao.CrudDAO;
import lk.ijse.projectharbourmaster.entity.Weather_email;

import java.sql.SQLException;

public interface WeatherEmailDAO extends CrudDAO<Weather_email> {
    public boolean isUpdatableDay(String nextDate) throws SQLException;

}
