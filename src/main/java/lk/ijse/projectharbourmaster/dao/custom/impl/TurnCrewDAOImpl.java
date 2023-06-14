package lk.ijse.projectharbourmaster.dao.custom.impl;

import lk.ijse.projectharbourmaster.dao.custom.TurnCrewDAO;
import lk.ijse.projectharbourmaster.dto.tm.CrewTM;
import lk.ijse.projectharbourmaster.entity.Turn_crew;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TurnCrewDAOImpl implements TurnCrewDAO {

    @Override
    public List<Turn_crew> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean add(Turn_crew entity) throws SQLException, IOException {
        return false;
    }

    @Override
    public boolean update(Turn_crew entity, String id) throws SQLException, IOException {
        return false;
    }

    @Override
    public Turn_crew search(String id) throws SQLException, IOException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean placeCrewInTurn(List<Turn_crew> crewTMList, String turnId) throws SQLException {
        String sql = "INSERT INTO turn_crew VALUES(? , ?)";

        for (int i = 0; i < crewTMList.size(); i++){
            boolean isInserted = CrudUtil.execute(sql , crewTMList.get(i).getNic() ,turnId);

            if (!isInserted){
                return false;
            }

            if (i == (crewTMList.size()-1)){
                return true;

            }
        }

        return false;

    }

}
