package lk.ijse.projectharbourmaster.bo.custom;

import lk.ijse.projectharbourmaster.bo.SuperBO;
import lk.ijse.projectharbourmaster.dto.TurnDTO;
import lk.ijse.projectharbourmaster.entity.Turn;

import java.sql.SQLException;
import java.util.List;

public interface MainMenuBO extends SuperBO {
    public List<String> getAllBoatsInSea() throws SQLException;
    public List<String> getAllCrewInSea() throws SQLException;
    public List<TurnDTO> getAllInCompletedTurnsInDanger() throws SQLException;
    public int getAvailableCount(String dockId) throws SQLException;
    public String searchWeatherSpecialCauses(String date) throws SQLException;


}
