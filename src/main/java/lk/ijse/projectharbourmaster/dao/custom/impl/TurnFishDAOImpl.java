package lk.ijse.projectharbourmaster.dao.custom.impl;

import lk.ijse.projectharbourmaster.dao.custom.TurnFishDAO;
import lk.ijse.projectharbourmaster.dto.tm.TurnFishSearchTM;
import lk.ijse.projectharbourmaster.dto.tm.TurnFishTM;
import lk.ijse.projectharbourmaster.entity.Turn_fish;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TurnFishDAOImpl implements TurnFishDAO {


    @Override
    public List<Turn_fish> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean add(Turn_fish entity) throws SQLException, IOException {
        return false;
    }

    @Override
    public boolean update(Turn_fish entity, String id) throws SQLException, IOException {
        return false;
    }

    @Override
    public Turn_fish search(String id) throws SQLException, IOException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }


    @Override
    public List<Turn_fish> getFishForTurnFishTBL(String turnId) throws SQLException {
        String sql = "SELECT * FROM turn_fish WHERE turnId = ? ";

        ResultSet rs = CrudUtil.execute(sql , turnId);
        List<Turn_fish> turnFishList = new ArrayList<>();

        while (rs.next()){
            turnFishList.add(
                    new Turn_fish(
                            rs.getString(1) ,
                            rs.getString(2) ,
                            rs.getDouble(3) ,
                            rs.getString(4)
                    )
            );
        }
        return turnFishList;

    }

    @Override
    public boolean insertTurnFishDetails(List<Turn_fish> turnFishList) throws SQLException {
        String sql = "INSERT INTO turn_fish VALUES (? , ? , ? , ?)";

        if (turnFishList.size() == 0){
            return true;
        }

        for (int i = 0; i < turnFishList.size(); i++){
            boolean isInserted = CrudUtil.execute(sql , turnFishList.get(i).getFishId() , turnFishList.get(i).getTurnId() , turnFishList.get(i).getWeight() ,  turnFishList.get(i).getDate() );

            if (!isInserted){
                return false;
            }

            if (i == (turnFishList.size()-1)){
                return true;
            }
        }

        return false;

    }

}
