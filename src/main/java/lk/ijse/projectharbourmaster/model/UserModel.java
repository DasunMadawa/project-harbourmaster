package lk.ijse.projectharbourmaster.model;

import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.User;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserModel {

    public static boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE user SET  nic = ? , userName = ? , password = ? WHERE userId = ?";

        return CrudUtil.execute(sql , user.getNic() , user.getUserName() , user.getPassword() , user.getUserId());


    }

    public static User searchUser(String userName) throws SQLException {
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

    public static User searchUserById(String userId) throws SQLException {
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


    public static boolean updateUserAll(User user1, User user2, User user3) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        try {
            con.setAutoCommit(false);

            if (updateUser(user1)){
                if (updateUser(user2)){
                    if (updateUser(user3)){
                        con.commit();
                        return true;

                    }

                }

            }

        }catch (SQLException e){
            System.out.println(e);

        }finally {
            con.rollback();
            con.setAutoCommit(true);

        }
        return false;

    }

}
