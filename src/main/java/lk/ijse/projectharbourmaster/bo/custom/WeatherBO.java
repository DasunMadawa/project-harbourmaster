package lk.ijse.projectharbourmaster.bo.custom;

import lk.ijse.projectharbourmaster.bo.SuperBO;
import lk.ijse.projectharbourmaster.dto.WeatherAPIDTO;
import lk.ijse.projectharbourmaster.dto.WeatherDTO;
import lk.ijse.projectharbourmaster.entity.Weather;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface WeatherBO extends SuperBO {
    public boolean isUpdatableDay(String nextDate) throws SQLException;
    public boolean addWeatherMgDates(String nextDate) throws SQLException, IOException;
    public boolean addWeatherRecords(WeatherDTO weatherDTO) throws SQLException, IOException;
    public List<WeatherDTO> getAllWeatherRecords() throws SQLException;
    public List<String> getAllEmailsCrew() throws SQLException;
    public List<String> getAllEmailsBoat() throws SQLException;
    public String searchWeatherSpecialCauses(String date) throws SQLException;
    public boolean updateWeather(WeatherDTO weather, String date, String time) throws SQLException;
    public WeatherDTO searchWeather(String userId , String date) throws SQLException;
    public boolean removeWeather(WeatherDTO weather) throws SQLException;
    public boolean sendWeatherReportsByEmail(List<String> crewEmailAr , WeatherAPIDTO weatherAPIDTO);

}
