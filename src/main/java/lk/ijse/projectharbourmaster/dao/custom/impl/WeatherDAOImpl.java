package lk.ijse.projectharbourmaster.dao.custom.impl;

import lk.ijse.projectharbourmaster.dao.custom.WeatherDAO;
import lk.ijse.projectharbourmaster.dto.WeatherDTO;
import lk.ijse.projectharbourmaster.dto.tm.WeatherTM;
import lk.ijse.projectharbourmaster.entity.Weather;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WeatherDAOImpl implements WeatherDAO {

    @Override
    public List<Weather> getAll() throws SQLException {
        String sql = "SELECT * FROM weather";

        List<Weather> weatherList = new ArrayList<>();

        ResultSet rs = CrudUtil.execute(sql);
        while (rs.next()){
            String userId = rs.getString(1);
            double windSpeed = rs.getDouble(2);
            String specialCauses = rs.getString(3);
            String date = rs.getString(4);
            String time = rs.getString(4);

            /*String threatLevel = "";

            if (windSpeed < 37) {
                threatLevel = "stable";
            } else if (windSpeed < 62) {
                threatLevel = "low";
            } else if (windSpeed < 88) {
                threatLevel = "medium";
            } else if (windSpeed < 118) {
                threatLevel = "high";
            } else {
                threatLevel = "critical";
            }

            if (specialCauses != null && !specialCauses.equals("")){
                threatLevel = "critical";
            }*/

            weatherList.add(new Weather(userId , windSpeed , specialCauses , date , time ));
        }
        return weatherList;

    }

    @Override
    public boolean add(Weather weather) throws SQLException, IOException {
        String sql = "INSERT INTO weather VALUES (? , ? , ? , ? , ?)";

        return CrudUtil.execute(sql , weather.getUserId() , weather.getWindSpeed() , weather.getSpecialCauses() , weather.getDate() , weather.getTime());

    }

    @Override
    public boolean update(Weather entity, String id) throws SQLException, IOException {
        return false;
    }

    @Override
    public Weather search(String id) throws SQLException, IOException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean updateWeather(Weather weather, String date, String time) throws SQLException {
        String sql = "UPDATE weather SET userId = ? , windSpeed = ? , specialCauses = ? , date = ? , time = ? WHERE date = ? && time = ?";

        return CrudUtil.execute(sql , weather.getUserId() , weather.getWindSpeed() , weather.getSpecialCauses() , weather.getDate() , weather.getTime() , date , time);

    }

    @Override
    public Weather searchWeather(String userId, String date) throws SQLException {
        String sql = "SELECT * FROM weather WHERE userId = ? && date = ?";

        ResultSet rs = CrudUtil.execute(sql, userId, date );
        if (rs.next()){
            String usrId = rs.getString(1);
            double windSpeed = rs.getDouble(2);
            String specialCauses = rs.getString(3);
            String time = rs.getString(5);

            return new Weather(usrId , windSpeed , specialCauses , date , time);
        }
        return null;

    }

    @Override
    public String searchWeatherSpecialCauses(String date) throws SQLException {
        String sql = "SELECT * FROM weather WHERE  date = ?";

        ResultSet rs = CrudUtil.execute(sql , date);

        while (rs.next()){
            if(rs.getString(3) != null){
                return rs.getString(3);
            }
        }
        return null;

    }

    @Override
    public boolean removeWeather(Weather weather) throws SQLException {
        String sql = "DELETE FROM weather WHERE date = ? && time = ?";

        return CrudUtil.execute(sql , weather.getDate() , weather.getTime());

    }

}
