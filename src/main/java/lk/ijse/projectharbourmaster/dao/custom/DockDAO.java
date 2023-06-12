package lk.ijse.projectharbourmaster.dao.custom;

import lk.ijse.projectharbourmaster.dao.CrudDAO;
import lk.ijse.projectharbourmaster.entity.Boat_dock;

import java.sql.SQLException;
import java.util.List;

public interface DockDAO extends CrudDAO<Boat_dock> {
    public int getAvailableCount(String dockId) throws SQLException;
    public List<String> getBoatIdForUndock(String dockId) throws SQLException;
    public boolean getBoatIdForDock(String boatId) throws SQLException;

}
