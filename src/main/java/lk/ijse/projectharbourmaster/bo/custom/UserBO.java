package lk.ijse.projectharbourmaster.bo.custom;

import lk.ijse.projectharbourmaster.bo.SuperBO;
import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.UserDTO;
import lk.ijse.projectharbourmaster.entity.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public interface UserBO extends SuperBO {
    public boolean updateUserAll(UserDTO userDTO1, UserDTO userDTO2, UserDTO userDTO3) throws SQLException;
    public boolean updateUser(UserDTO userDTO) throws SQLException, IOException;
    public UserDTO searchUserByUserName(String userId) throws SQLException;
    public UserDTO searchUserByUserId(String userId) throws SQLException, IOException;

}
