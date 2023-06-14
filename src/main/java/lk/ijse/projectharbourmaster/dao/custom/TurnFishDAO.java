package lk.ijse.projectharbourmaster.dao.custom;

import lk.ijse.projectharbourmaster.dao.CrudDAO;
import lk.ijse.projectharbourmaster.dto.tm.TurnFishTM;
import lk.ijse.projectharbourmaster.entity.Turn_fish;

import java.sql.SQLException;
import java.util.List;

public interface TurnFishDAO extends CrudDAO<Turn_fish> {
    public List<Turn_fish> getFishForTurnFishTBL(String turnId) throws SQLException;
    public boolean insertTurnFishDetails(List<Turn_fish> turnFishTMList) throws SQLException;


}
