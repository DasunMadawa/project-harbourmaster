package lk.ijse.projectharbourmaster.bo.custom;

import lk.ijse.projectharbourmaster.bo.SuperBO;
import lk.ijse.projectharbourmaster.dto.*;
import lk.ijse.projectharbourmaster.dto.tm.TurnFishTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface TurnBO extends SuperBO {
    public List<TurnDTO> getAllCompletedTurns() throws SQLException;
    public List<TurnDTO> getAllInCompletedTurns() throws SQLException;
    public List<String> getAllCrewInSea() throws SQLException;
//    public List<CrewDTO> getAllCrewInATurn(String turnIdSearch) throws SQLException;
    public List<CrewDTO> getAllCrewForTable() throws SQLException;
    public List<String> getAllBoatIds() throws SQLException;
    public List<String> getAllBoatsInSea() throws SQLException;
    public String generateNextTurnId() throws SQLException;
    public CrewDTO searchCrew(String id) throws SQLException, IOException;
    public BoatDTO searchBoat(String id) throws SQLException, IOException;
    public boolean startTurn(TurnDTO turnDTO) throws SQLException;
    public CustomDTO searchFishTM(String fishId, Double fishWeight) throws SQLException, IOException;
    public TurnDTO searchTurn(String turnId) throws SQLException, IOException;
    public boolean endTurn(TurnDTO turnDTO, List<TurnFishTM> turnFishTMList) throws SQLException;
    public List<TurnFishDTO> getFishForTurnFishTBL(String turnId) throws SQLException, IOException;
    public FishDTO searchFish(String fishId) throws SQLException, IOException;

}
