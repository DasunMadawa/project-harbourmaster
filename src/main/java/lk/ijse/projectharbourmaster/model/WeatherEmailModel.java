package lk.ijse.projectharbourmaster.model;

import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class WeatherEmailModel {
    public static boolean isUpdatableDay(String nextDate) throws SQLException {
        String sql = "SELECT day , DATE(curDate()) as nextDay FROM weather_email ORDER BY day DESC LIMIT 1";
        ResultSet rs = CrudUtil.execute(sql);

        if (rs.next()){
            String lastDate = rs.getString(1);
            String currentDate = rs.getString(2);

            System.out.println( lastDate + " " + currentDate);

            if (lastDate.equals(currentDate)){
                return false;
            }
            return true;

        }else {
            return true;
        }

    }

    public static boolean insert(String day) throws SQLException {
        String sql = "INSERT INTO weather_email VALUES (?)";

        return CrudUtil.execute(sql , day);
    }
}
