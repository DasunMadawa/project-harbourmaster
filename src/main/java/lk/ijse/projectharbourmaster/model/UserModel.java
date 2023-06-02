package lk.ijse.projectharbourmaster.model;

import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.UserDTO;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    public static boolean updateUser(UserDTO userDTO) throws SQLException {
        String sql = "UPDATE user SET  nic = ? , userName = ? , password = ? WHERE userId = ?";

        return CrudUtil.execute(sql , userDTO.getNic() , userDTO.getUserName() , userDTO.getPassword() , userDTO.getUserId());


    }

    public static UserDTO searchUser(String userName) throws SQLException {
        String sql = "SELECT * FROM user WHERE userName = ? ";

        ResultSet rs = CrudUtil.execute(sql, userName);

        if (rs.next()){
            String usrId = rs.getString(1);
            String nic = rs.getString(2);
            String usrName = rs.getString(3);
            String password = rs.getString(4);

            return new UserDTO(usrId , nic , usrName , password);

        }

        return null;
    }

    public static UserDTO searchUserById(String userId) throws SQLException {
        String sql = "SELECT * FROM user WHERE userId = ? ";

        ResultSet rs = CrudUtil.execute(sql, userId);

        if (rs.next()){
            String usrId = rs.getString(1);
            String nic = rs.getString(2);
            String usrName = rs.getString(3);
            String password = rs.getString(4);

            return new UserDTO(usrId , nic , usrName , password);

        }

        return null;
    }


    public static boolean updateUserAll(UserDTO userDTO1, UserDTO userDTO2, UserDTO userDTO3) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        try {
            con.setAutoCommit(false);

            if (updateUser(userDTO1)){
                if (updateUser(userDTO2)){
                    if (updateUser(userDTO3)){
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
