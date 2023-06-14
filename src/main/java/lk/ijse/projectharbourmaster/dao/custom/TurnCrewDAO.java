package lk.ijse.projectharbourmaster.dao.custom;

import lk.ijse.projectharbourmaster.dao.CrudDAO;
import lk.ijse.projectharbourmaster.dto.tm.CrewTM;
import lk.ijse.projectharbourmaster.entity.Turn_crew;

import java.sql.SQLException;
import java.util.List;

public interface TurnCrewDAO extends CrudDAO<Turn_crew> {

    public boolean placeCrewInTurn(List<Turn_crew> crewTMList , String turnId) throws SQLException;


}
