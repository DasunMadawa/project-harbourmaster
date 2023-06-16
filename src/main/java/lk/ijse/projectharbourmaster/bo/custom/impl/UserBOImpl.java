package lk.ijse.projectharbourmaster.bo.custom.impl;

import lk.ijse.projectharbourmaster.bo.custom.UserBO;
import lk.ijse.projectharbourmaster.dao.DAOFactory;
import lk.ijse.projectharbourmaster.dao.custom.UserDAO;
import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.UserDTO;
import lk.ijse.projectharbourmaster.entity.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean updateUserAll(UserDTO userDTO1, UserDTO userDTO2, UserDTO userDTO3) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        try {
            con.setAutoCommit(false);

            if (userDAO.update(
                    new User(
                            userDTO1.getUserId(),
                            userDTO1.getNic(),
                            userDTO1.getUserName(),
                            userDTO1.getPassword()
                    ),
                    userDTO1.getUserId()
            )
            ) {
                if (userDAO.update(
                        new User(
                                userDTO2.getUserId(),
                                userDTO2.getNic(),
                                userDTO2.getUserName(),
                                userDTO2.getPassword()
                        ),
                        userDTO2.getUserId()
                )
                ) {
                    if (userDAO.update(
                            new User(
                                    userDTO2.getUserId(),
                                    userDTO2.getNic(),
                                    userDTO2.getUserName(),
                                    userDTO2.getPassword()
                            ),
                            userDTO2.getUserId()
                    )
                    ) {
                        con.commit();
                        return true;

                    }

                }

            }

        } catch (SQLException e) {
            System.out.println(e);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            con.rollback();
            con.setAutoCommit(true);

        }
        return false;


    }

    @Override
    public boolean updateUser(UserDTO userDTO) throws SQLException, IOException {
        return userDAO.update(
                new User(
                        userDTO.getUserId(),
                        userDTO.getNic(),
                        userDTO.getUserName(),
                        userDTO.getPassword()
                ) ,
                userDTO.getUserId()
        );

    }

    @Override
    public UserDTO searchUserByUserName(String userName) throws SQLException {
        User user = userDAO.searchUserByUserName(userName);

        return new UserDTO(
                user.getUserId(),
                user.getNic(),
                user.getUserName(),
                user.getPassword()
        );

    }

    @Override
    public UserDTO searchUserByUserId(String userId) throws SQLException, IOException {
        User user = userDAO.search(userId);

        return new UserDTO(
                user.getUserId(),
                user.getNic(),
                user.getUserName(),
                user.getPassword()
        );

    }

}
