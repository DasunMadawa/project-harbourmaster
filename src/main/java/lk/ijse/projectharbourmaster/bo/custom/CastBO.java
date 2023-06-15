package lk.ijse.projectharbourmaster.bo.custom;

import lk.ijse.projectharbourmaster.bo.SuperBO;

import java.sql.SQLException;

public interface CastBO extends SuperBO {
    public boolean cast(String selectedType , String message) throws SQLException;

}
