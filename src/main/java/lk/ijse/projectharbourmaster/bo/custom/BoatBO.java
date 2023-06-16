package lk.ijse.projectharbourmaster.bo.custom;

import lk.ijse.projectharbourmaster.bo.SuperBO;
import lk.ijse.projectharbourmaster.dto.BoatDTO;
import lk.ijse.projectharbourmaster.dto.CustomDTO;
import lk.ijse.projectharbourmaster.dto.tm.BoatTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface BoatBO extends SuperBO {
    public List<CustomDTO> getAllBoats() throws SQLException;
    public boolean addBoat(BoatDTO boatDTO) throws SQLException, IOException;
    public boolean updateBoat(BoatDTO boatDTO , String boatId) throws SQLException, IOException;
    public BoatDTO searchBoat(String boatId) throws SQLException, IOException;
    public boolean deleteBoat(String boatId) throws SQLException;
    public List<String> getAllEmails() throws SQLException;

}
