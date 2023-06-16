package lk.ijse.projectharbourmaster.model;

import lk.ijse.projectharbourmaster.dto.WeatherDTO;
import lk.ijse.projectharbourmaster.dto.tm.WeatherTM;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WeatherModel {
    public static boolean updateWeather(WeatherDTO weatherDTO, String date, String time) throws SQLException {
        String sql = "UPDATE weather SET userId = ? , windSpeed = ? , specialCauses = ? , date = ? , time = ? WHERE date = ? && time = ?";

        return CrudUtil.execute(sql , weatherDTO.getUserId() , weatherDTO.getWindSpeed() , weatherDTO.getSpecialCauses() , weatherDTO.getDate() , weatherDTO.getTime() , date , time);

    }

    public static boolean insertWeather(WeatherDTO weatherDTO) throws SQLException {
        String sql = "INSERT INTO weather VALUES (? , ? , ? , ? , ?)";

        return CrudUtil.execute(sql , weatherDTO.getUserId() , weatherDTO.getWindSpeed() , weatherDTO.getSpecialCauses() , weatherDTO.getDate() , weatherDTO.getTime());

    }

    public static WeatherDTO searchWeather(String userId , String date) throws SQLException {
        String sql = "SELECT * FROM weather WHERE userId = ? && date = ?";

        ResultSet rs = CrudUtil.execute(sql, userId, date );
        if (rs.next()){
            String usrId = rs.getString(1);
            double windSpeed = rs.getDouble(2);
            String specialCauses = rs.getString(3);
            String time = rs.getString(5);

            return new WeatherDTO(usrId , windSpeed , specialCauses , date , time , null);
        }
        return null;

    }

    public static String searchWeatherSpecialCauses(String date) throws SQLException {
        String sql = "SELECT * FROM weather WHERE  date = ?";

        ResultSet rs = CrudUtil.execute(sql , date);

        while (rs.next()){
            if(rs.getString(3) != null){
                return rs.getString(3);
            }
        }
        return null;

    }

    public static List<WeatherTM> getAll() throws SQLException {
        String sql = "SELECT * FROM weather";

        List<WeatherTM> weatherList = new ArrayList<>();

        ResultSet rs = CrudUtil.execute(sql);
        while (rs.next()){
            String date = rs.getString(4);
            double windSpeed = rs.getDouble(2);
            String specialCauses = rs.getString(3);
            String threatLevel = "";

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
            }

            weatherList.add(new WeatherTM(date , windSpeed , specialCauses , threatLevel));
        }
        return weatherList;

    }

    public static boolean removeWeather(WeatherDTO weatherDTO) throws SQLException {
        String sql = "DELETE FROM weather WHERE date = ? && time = ?";

        return CrudUtil.execute(sql , weatherDTO.getDate() , weatherDTO.getTime());

    }



}
