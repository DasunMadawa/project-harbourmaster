package lk.ijse.projectharbourmaster.dao.custom.impl;

import lk.ijse.projectharbourmaster.dao.custom.UserDAO;
import lk.ijse.projectharbourmaster.dto.UserDTO;
import lk.ijse.projectharbourmaster.entity.User;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public List<User> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean add(User entity) throws SQLException, IOException {
        return false;
    }

    @Override
    public boolean update(User user, String id) throws SQLException, IOException {
        String sql = "UPDATE user SET  nic = ? , userName = ? , password = ? WHERE userId = ?";

        return CrudUtil.execute(sql , user.getNic() , user.getUserName() , user.getPassword() , id);

    }

    @Override
    public User search(String userId) throws SQLException, IOException {
        String sql = "SELECT * FROM user WHERE userId = ? ";

        ResultSet rs = CrudUtil.execute(sql, userId);

        if (rs.next()){
            String usrId = rs.getString(1);
            String nic = rs.getString(2);
            String usrName = rs.getString(3);
            String password = rs.getString(4);

            return new User(usrId , nic , usrName , password);

        }

        return null;

    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }


    @Override
    public User searchUserByUserName(String userName) throws SQLException {
        String sql = "SELECT * FROM user WHERE userName = ? ";

        ResultSet rs = CrudUtil.execute(sql, userName);

        if (rs.next()){
            String usrId = rs.getString(1);
            String nic = rs.getString(2);
            String usrName = rs.getString(3);
            String password = rs.getString(4);

            return new User(usrId , nic , usrName , password);

        }

        return null;

    }
}
