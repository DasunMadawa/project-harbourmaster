package lk.ijse.projectharbourmaster.bo.custom.impl;

import lk.ijse.projectharbourmaster.bo.custom.BoatBO;
import lk.ijse.projectharbourmaster.dao.DAOFactory;
import lk.ijse.projectharbourmaster.dao.custom.BoatDAO;
import lk.ijse.projectharbourmaster.dao.custom.QueryDAO;
import lk.ijse.projectharbourmaster.dto.BoatDTO;
import lk.ijse.projectharbourmaster.dto.CustomDTO;
import lk.ijse.projectharbourmaster.entity.Boat;
import lk.ijse.projectharbourmaster.entity.CustomEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoatBOImpl implements BoatBO {
    BoatDAO boatDAO = (BoatDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOAT);
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY_DAO);

    @Override
    public List<CustomDTO> getAllBoats() throws SQLException {
        List<CustomDTO> boatDTOList = new ArrayList<>();

        for (CustomEntity customEntity : queryDAO.getAllBoats()) {
            boatDTOList.add(new CustomDTO(
                            customEntity.getBoatId(),
                            customEntity.getBoatOwner(),
                            customEntity.getBoatName(),
                            customEntity.getBoatType(),
                            customEntity.getBoatNoCrew(),
                            customEntity.getFuelTankCap(),
                            customEntity.getFreshWaterCap(),
                            customEntity.getMaxWeight(),
                            customEntity.getDockId()
                    )
            );

        }

        return boatDTOList;
    }

    @Override
    public boolean addBoat(BoatDTO boatDTO) throws SQLException, IOException {
        return boatDAO.add(new Boat(
                        boatDTO.getBoatId(),
                        boatDTO.getBoatOwner(),
                        boatDTO.getBoatName(),
                        boatDTO.getBoatType(),
                        boatDTO.getNoCrew(),
                        boatDTO.getFuelCap(),
                        boatDTO.getWaterCap(),
                        boatDTO.getMaxWeight(),
                        boatDTO.getEmail()
                )
        );

    }

    @Override
    public boolean updateBoat(BoatDTO boatDTO, String id) throws SQLException, IOException {
        return boatDAO.update(
                new Boat(
                        boatDTO.getBoatId(),
                        boatDTO.getBoatOwner(),
                        boatDTO.getBoatName(),
                        boatDTO.getBoatType(),
                        boatDTO.getNoCrew(),
                        boatDTO.getFuelCap(),
                        boatDTO.getWaterCap(),
                        boatDTO.getMaxWeight(),
                        boatDTO.getEmail()
                ),

                id
        );

    }

    @Override
    public BoatDTO searchBoat(String boatId) throws SQLException, IOException {
        Boat boat = boatDAO.search(boatId);

        if (boat == null){
            return null;
        }

        return new BoatDTO(
                boat.getBoatId(),
                boat.getBoatOwner(),
                boat.getBoatName(),
                boat.getBoatType(),
                boat.getNoCrew(),
                boat.getFuelTankCap(),
                boat.getFreshWaterCap(),
                boat.getMaxWeight(),
                boat.getBoatOwnerEmail()
        );


    }

    @Override
    public boolean deleteBoat(String boatId) throws SQLException {
        return boatDAO.delete(boatId);

    }

    @Override
    public List<String> getAllEmails() throws SQLException {
        return boatDAO.getAllEmails();

    }

}
