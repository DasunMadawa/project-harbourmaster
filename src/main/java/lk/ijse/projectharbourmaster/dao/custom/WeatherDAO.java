package lk.ijse.projectharbourmaster.dao.custom;

import lk.ijse.projectharbourmaster.dao.CrudDAO;
import lk.ijse.projectharbourmaster.dto.WeatherDTO;
import lk.ijse.projectharbourmaster.entity.Weather;

import java.sql.SQLException;

public interface WeatherDAO extends CrudDAO<Weather> {
    public boolean updateWeather(Weather weather, String date, String time) throws SQLException;
    public Weather searchWeather(String userId , String date) throws SQLException;
    public String searchWeatherSpecialCauses(String date) throws SQLException;
    public boolean removeWeather(Weather weather) throws SQLException;

}
