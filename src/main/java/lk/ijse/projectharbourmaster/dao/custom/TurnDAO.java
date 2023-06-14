package lk.ijse.projectharbourmaster.dao.custom;

import lk.ijse.projectharbourmaster.dao.CrudDAO;
import lk.ijse.projectharbourmaster.dto.TurnDTO;
import lk.ijse.projectharbourmaster.dto.tm.TurnTM;
import lk.ijse.projectharbourmaster.entity.Turn;
import lk.ijse.projectharbourmaster.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TurnDAO extends CrudDAO<Turn> {
    public String getLastTurnID() throws SQLException;
    public List<String> getAllBoatsInSea() throws SQLException;
    public boolean registerTurn(Turn turn) throws SQLException;
    public boolean endTurnDetailsUpdate(Turn turn) throws SQLException;
    public List<Turn> getAllCompletedTurns() throws SQLException;
    public List<Turn> getAllInCompletedTurns() throws SQLException;
    public List<Turn> getAllInCompletedTurnsInDanger(List<Turn> allInCompletedTurns) throws SQLException;


}
