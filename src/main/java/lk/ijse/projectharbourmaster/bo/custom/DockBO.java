package lk.ijse.projectharbourmaster.bo.custom;

import lk.ijse.projectharbourmaster.bo.SuperBO;
import lk.ijse.projectharbourmaster.dto.BoatDockDTO;
import lk.ijse.projectharbourmaster.dto.CrewDTO;
import lk.ijse.projectharbourmaster.entity.Boat_dock;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface DockBO extends SuperBO {
    public boolean dockBoats(BoatDockDTO boatDockDTO) throws SQLException, IOException;
    public boolean undockBoats(BoatDockDTO boatDockDTO , String id ) throws SQLException, IOException;
    public BoatDockDTO searchBoat(String id) throws SQLException, IOException;
    public boolean deleteDock(String id) throws SQLException, IOException;
    public int getAvailableCount(String dockId) throws SQLException;
    public List<String> getBoatIdForUndock(String dockId) throws SQLException;
    public boolean getBoatIdForDock(String boatId) throws SQLException;
    public List<BoatDockDTO> getAll() throws SQLException;
    public List<String> getAllBoatIds() throws SQLException;


}
