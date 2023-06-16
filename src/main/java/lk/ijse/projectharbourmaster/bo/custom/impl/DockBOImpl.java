package lk.ijse.projectharbourmaster.bo.custom.impl;

import lk.ijse.projectharbourmaster.bo.custom.BoatBO;
import lk.ijse.projectharbourmaster.bo.custom.DockBO;
import lk.ijse.projectharbourmaster.dao.DAOFactory;
import lk.ijse.projectharbourmaster.dao.custom.BoatDAO;
import lk.ijse.projectharbourmaster.dao.custom.DockDAO;
import lk.ijse.projectharbourmaster.dto.BoatDockDTO;
import lk.ijse.projectharbourmaster.dto.CrewDTO;
import lk.ijse.projectharbourmaster.entity.Boat_dock;
import lk.ijse.projectharbourmaster.entity.Dock;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DockBOImpl implements DockBO {
    DockDAO dockDAO = (DockDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DOCK);
    BoatDAO boatDAO = (BoatDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOAT);

    @Override
    public boolean dockBoats(BoatDockDTO boatDockDTO) throws SQLException, IOException {
        return dockDAO.add(
                new Boat_dock(
                        boatDockDTO.getDockId(),
                        boatDockDTO.getBoatId(),
                        boatDockDTO.getInDate(),
                        boatDockDTO.getOutDate()
                )
        );

    }

    @Override
    public boolean undockBoats(BoatDockDTO boatDockDTO, String id) throws SQLException, IOException {
        return dockDAO.update(
                new Boat_dock(
                        boatDockDTO.getDockId(),
                        boatDockDTO.getBoatId(),
                        boatDockDTO.getInDate(),
                        boatDockDTO.getOutDate()
                ),
                id
        );

    }

    @Override
    public BoatDockDTO searchBoat(String id) throws SQLException, IOException {
        Boat_dock boat_dock = dockDAO.search(id);

        return new BoatDockDTO(
                boat_dock.getDockId(),
                boat_dock.getBoatId(),
                boat_dock.getInDate(),
                boat_dock.getOutDate()
        );

    }

    @Override
    public boolean deleteDock(String id) throws SQLException, IOException {
        return dockDAO.delete(id);

    }

    @Override
    public int getAvailableCount(String dockId) throws SQLException {
        return dockDAO.getAvailableCount(dockId);

    }

    @Override
    public List<String> getBoatIdForUndock(String dockId) throws SQLException {
        return dockDAO.getBoatIdForUndock(dockId);

    }

    @Override
    public boolean getBoatIdForDock(String boatId) throws SQLException {
        return dockDAO.getBoatIdForDock(boatId);

    }

    @Override
    public List<BoatDockDTO> getAll() throws SQLException {
        List<BoatDockDTO> boatDockDTOList = new ArrayList<>();

        for (Boat_dock boat_dock : dockDAO.getAll()) {
            boatDockDTOList.add(
                    new BoatDockDTO(
                            boat_dock.getDockId(),
                            boat_dock.getBoatId(),
                            boat_dock.getInDate(),
                            boat_dock.getOutDate()
                    )
            );

        }
        return boatDockDTOList;

    }

    @Override
    public List<String> getAllBoatIds() throws SQLException {
        return boatDAO.getAllIds();

    }

}
