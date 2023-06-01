package lk.ijse.projectharbourmaster.model;

import javafx.collections.FXCollections;
import lk.ijse.projectharbourmaster.dto.Fish;
import lk.ijse.projectharbourmaster.dto.tm.TurnFishSearchTM;
import lk.ijse.projectharbourmaster.dto.tm.TurnFishTM;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TurnFishModel {

    public static TurnFishTM searchFishTM(String fishId, Double fishWeight) throws SQLException {
        Fish fish = FIshModel.searchFish(fishId);

        return new TurnFishTM(fish.getFishId() , fish.getFishName() , fish.getUnitPrice() ,  fishWeight , null);

    }

    public static boolean insertTurnFishDetails(List<TurnFishTM> turnFishTMList , String turnId , String date) throws SQLException {
        String sql = "INSERT INTO turn_fish VALUES (? , ? , ? , ?)";

        if (turnFishTMList.size() == 0){
            return true;
        }

        for (int i = 0; i < turnFishTMList.size(); i++){
            boolean isInserted = CrudUtil.execute(sql , turnFishTMList.get(i).getFishId() , turnId , turnFishTMList.get(i).getQty() ,  date);

            if (!isInserted){
                return false;
            }

            if (i == (turnFishTMList.size()-1)){
                return true;
            }
        }

        return false;
    }

    public static List<TurnFishSearchTM> getFishForTurnFishTBL(String turnId) throws SQLException {
        String sql = "SELECT * FROM turn_fish WHERE turnId = ? ";

        ResultSet rs = CrudUtil.execute(sql , turnId);
        List<TurnFishSearchTM> turnFishTMS = new ArrayList<>();

        while (rs.next()){
            turnFishTMS.add(
                    new TurnFishSearchTM(
                        rs.getString(1) ,
                        rs.getString(2) ,
                        rs.getDouble(3)
                    )
            );

        }
        return turnFishTMS;

    }

}
