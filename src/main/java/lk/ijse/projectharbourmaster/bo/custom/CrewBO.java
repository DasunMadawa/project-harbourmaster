package lk.ijse.projectharbourmaster.bo.custom;

import lk.ijse.projectharbourmaster.bo.SuperBO;
import lk.ijse.projectharbourmaster.dto.BoatDTO;
import lk.ijse.projectharbourmaster.dto.CrewDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface CrewBO extends SuperBO {
    public boolean addCrew(CrewDTO crewDTO) throws SQLException, IOException;
    public boolean updateCrew(CrewDTO crewDTO , String id ) throws SQLException, IOException;
    public CrewDTO searchCrew(String id) throws SQLException, IOException;
    public boolean deleteCrew(String id) throws SQLException, IOException;
    public List<CrewDTO> getAllCrew() throws SQLException;
    public List<String> getAllEmails() throws SQLException;

}
