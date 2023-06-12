package lk.ijse.projectharbourmaster.bo.custom.impl;

import lk.ijse.projectharbourmaster.bo.custom.BoatBO;
import lk.ijse.projectharbourmaster.dao.DAOFactory;
import lk.ijse.projectharbourmaster.dao.custom.BoatDAO;
import lk.ijse.projectharbourmaster.dto.BoatDTO;
import lk.ijse.projectharbourmaster.dto.tm.BoatTM;

import java.sql.SQLException;
import java.util.List;

public class BoatBOImpl implements BoatBO {
    BoatDAO boatDAO = (BoatDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Boat);

    @Override
    public List<BoatTM> getAllBoats() throws SQLException {
        boatDAO.getAll();
        return null;
    }

    @Override
    public boolean addBoat(BoatDTO dto) throws SQLException {
        return false;
    }

    @Override
    public boolean updateBoat(BoatDTO dto, String id) throws SQLException {
        return false;
    }

    @Override
    public BoatDTO searchBoat(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean deleteBoat(String id) throws SQLException {
        return false;
    }
}
