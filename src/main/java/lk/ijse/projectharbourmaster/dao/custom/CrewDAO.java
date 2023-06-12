package lk.ijse.projectharbourmaster.dao.custom;

import lk.ijse.projectharbourmaster.dao.CrudDAO;
import lk.ijse.projectharbourmaster.entity.Crew;

import java.sql.SQLException;
import java.util.List;

public interface CrewDAO extends CrudDAO<Crew> {
    public List<String> getAllEmails() throws SQLException;

}
