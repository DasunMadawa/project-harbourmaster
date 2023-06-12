package lk.ijse.projectharbourmaster.dao.custom;

import lk.ijse.projectharbourmaster.dao.CrudDAO;
import lk.ijse.projectharbourmaster.dto.BoatDTO;
import lk.ijse.projectharbourmaster.dto.tm.BoatTM;

import java.sql.SQLException;
import java.util.List;

public interface BoatDAO extends CrudDAO<BoatDTO , BoatTM> {
    public List<String> loadAllIds() throws SQLException;
    public List<String> getAllEmails() throws SQLException;


}
