package lk.ijse.projectharbourmaster.bo.custom;

import lk.ijse.projectharbourmaster.dto.BoatDTO;
import lk.ijse.projectharbourmaster.dto.tm.BoatTM;

import java.sql.SQLException;
import java.util.List;

public interface BoatBO {
    public List<BoatTM> getAllBoats() throws SQLException;
    public boolean addBoat(BoatDTO dto) throws SQLException;
    public boolean updateBoat(BoatDTO dto , String id) throws SQLException;
    public BoatDTO searchBoat(String id) throws SQLException;
    public boolean deleteBoat(String id) throws SQLException;

}
