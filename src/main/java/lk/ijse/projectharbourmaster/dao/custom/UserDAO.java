package lk.ijse.projectharbourmaster.dao.custom;

import lk.ijse.projectharbourmaster.dao.CrudDAO;
import lk.ijse.projectharbourmaster.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    public User searchUserByUserName(String userId) throws SQLException;

}
