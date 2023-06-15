package lk.ijse.projectharbourmaster.dao.custom.impl;

import lk.ijse.projectharbourmaster.dao.custom.WeatherEmailDAO;
import lk.ijse.projectharbourmaster.entity.Weather_email;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class WeatherEmailDAOImpl implements WeatherEmailDAO {

    @Override
    public List<Weather_email> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean add(Weather_email weather_email) throws SQLException, IOException {
        String sql = "INSERT INTO weather_email VALUES (?)";

        return CrudUtil.execute(sql , weather_email.getDay() );

    }

    @Override
    public boolean update(Weather_email entity, String id) throws SQLException, IOException {
        return false;
    }

    @Override
    public Weather_email search(String id) throws SQLException, IOException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean isUpdatableDay(String nextDate) throws SQLException {
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
}
