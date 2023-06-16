package lk.ijse.projectharbourmaster.bo.custom.impl;

import lk.ijse.projectharbourmaster.bo.custom.TurnBO;
import lk.ijse.projectharbourmaster.dao.DAOFactory;
import lk.ijse.projectharbourmaster.dao.custom.*;
import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.*;
import lk.ijse.projectharbourmaster.dto.tm.CrewTM;
import lk.ijse.projectharbourmaster.dto.tm.TurnFishTM;
import lk.ijse.projectharbourmaster.entity.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TurnBOImpl implements TurnBO {
    TurnDAO turnDAO = (TurnDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TURN);
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY_DAO);
    CrewDAO crewDAO = (CrewDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CREW);
    BoatDAO boatDAO = (BoatDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOAT);
    TurnCrewDAO turnCrewDAO = (TurnCrewDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TURN_CREW);
    TurnFishDAO turnFishDAO = (TurnFishDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TURN_FISH);
    FishDAO fishDAO = (FishDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.FISH);

    @Override
    public List<TurnDTO> getAllCompletedTurns() throws SQLException {
        List<TurnDTO> turnDTOList = new ArrayList<>();

        for (Turn turn : turnDAO.getAllCompletedTurns()) {
            turnDTOList.add(
                    new TurnDTO(
                            turn.getTurnId(),
                            turn.getBoatId(),
                            turn.getCapNIC(),
                            turn.getCrewCount(),
                            turn.getOutDate(),
                            turn.getOutTime(),
                            turn.getInDate(),
                            turn.getInTime(),
                            null
                    )
            );
        }
        return turnDTOList;

    }

    @Override
    public List<TurnDTO> getAllInCompletedTurns() throws SQLException {
        List<TurnDTO> turnDTOList = new ArrayList<>();

        for (Turn turn : turnDAO.getAllInCompletedTurns()) {
            turnDTOList.add(
                    new TurnDTO(
                            turn.getTurnId(),
                            turn.getBoatId(),
                            turn.getCapNIC(),
                            turn.getCrewCount(),
                            turn.getOutDate(),
                            turn.getOutTime(),
                            turn.getInDate(),
                            turn.getInTime(),
                            null
                    )
            );
        }
        return turnDTOList;

    }

    @Override
    public List<String> getAllCrewInSea() throws SQLException {
        return queryDAO.getAllCrewInSea();

    }

    @Override
    public List<CrewDTO> getAllCrewForTable() throws SQLException {
        List<CrewDTO> crewDTOList = new ArrayList<>();

        for (Crew crew : crewDAO.getAll()) {
            crewDTOList.add(
                    new CrewDTO(crew.getNic(),
                            crew.getName(),
                            crew.getPhoto(),
                            (LocalDate.now().getYear() - Integer.valueOf(crew.getBod().substring(0, 4))) + "",
                            crew.getAddress(),
                            crew.getGender(),
                            crew.getEmail(),
                            crew.getContact()
                    )
            );
        }
        return crewDTOList;

    }

    @Override
    public List<String> getAllBoatIds() throws SQLException {
        return boatDAO.getAllIds();

    }

    @Override
    public List<String> getAllBoatsInSea() throws SQLException {
        return turnDAO.getAllBoatsInSea();

    }

    @Override
    public String generateNextTurnId() throws SQLException {
        String lastTurnID = turnDAO.getLastTurnID();

        return String.format("T%03d", Integer.parseInt(lastTurnID.substring(1) + 1));

    }

    @Override
    public CrewDTO searchCrew(String id) throws SQLException, IOException {
        Crew crew = crewDAO.search(id);

        return new CrewDTO(crew.getNic(),
                crew.getName(),
                crew.getPhoto(),
                crew.getBod(),
                crew.getAddress(),
                crew.getGender(),
                crew.getEmail(),
                crew.getContact()
        );

    }

    @Override
    public BoatDTO searchBoat(String boatId) throws SQLException, IOException {
        Boat boat = boatDAO.search(boatId);

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
    public boolean startTurn(TurnDTO turnDTO) throws SQLException {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean isUpdatedTurn = turnDAO.registerTurn(
                    new Turn(
                            turnDTO.getTurnId(),
                            turnDTO.getBoatId(),
                            turnDTO.getCapNIC(),
                            turnDTO.getCrewCount(),
                            turnDTO.getOutDate(),
                            turnDTO.getOutTime(),
                            turnDTO.getInDate(),
                            turnDTO.getInTime()
                    )
            );

            if (isUpdatedTurn) {
                List<Turn_crew> turn_crewList = new ArrayList<>();

                for (CrewTM crewTM : turnDTO.getCrewTM()) {
                    turn_crewList.add(
                            new Turn_crew(
                                    crewTM.getNic(),
                                    null
                            )
                    );
                }

                turn_crewList.add( new Turn_crew( turnDTO.getCapNIC() , null ) );

                boolean isUpdatedTurnCrew = turnCrewDAO.placeCrewInTurn(turn_crewList , turnDTO.getTurnId());

                if (isUpdatedTurnCrew) {
                    con.commit();
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
            return false;

        } finally {
            con.rollback();
            con.setAutoCommit(true);

        }


    }

    @Override
    public CustomDTO searchFishTM(String fishId, Double fishWeight) throws SQLException, IOException {
        Fish fish = fishDAO.search(fishId);

        return new CustomDTO(fish.getFishId() , fish.getName() , fish.getUnitPrice() , fishWeight);

    }

    @Override
    public TurnDTO searchTurn(String turnId) throws SQLException, IOException {
        Turn turn = turnDAO.search(turnId);
        List<CrewTM> crewTMList = new ArrayList<>();

        for (CustomEntity customEntity : queryDAO.getAllCrewInATurn(turnId) ) {
            crewTMList.add(
                    new CrewTM(
                            customEntity.getNic(),
                            customEntity.getCrewName(),
                            customEntity.getCrewAddress(),
                            customEntity.getCrewContact(),
                            customEntity.getCrewBod()
                    )
            );
        }

        return new TurnDTO(
                turn.getTurnId(),
                turn.getBoatId(),
                turn.getCapNIC(),
                turn.getCrewCount(),
                turn.getOutDate(),
                turn.getOutTime(),
                turn.getInDate(),
                turn.getInTime(),
                crewTMList
        );

    }

    @Override
    public boolean endTurn(TurnDTO turnDTO, List<TurnFishTM> turnFishTMList) throws SQLException {
        Connection con = null;

        try{
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            List<Turn_fish> turnFishList = new ArrayList<>();

            for (TurnFishTM turnFishTM : turnFishTMList ) {
                turnFishList.add(
                        new Turn_fish(
                                turnFishTM.getFishId(),
                                turnDTO.getTurnId(),
                                turnFishTM.getQty(),
                                turnDTO.getInDate()
                        )
                );
            }

            boolean isInserted = turnFishDAO.insertTurnFishDetails( turnFishList );

            if (isInserted){
                boolean isUpdated = turnDAO.endTurnDetailsUpdate(
                        new Turn(
                                turnDTO.getTurnId(),
                                null,
                                null,
                                0,
                                null,
                                null,
                                turnDTO.getInDate(),
                                turnDTO.getInTime()
                        )
                );

                if (isUpdated){
                    con.commit();
                    return true;
                }

            }
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            con.rollback();
            return false;
        }finally {
            con.rollback();
            con.setAutoCommit(true);
        }


    }

    @Override
    public List<TurnFishDTO> getFishForTurnFishTBL(String turnId) throws SQLException, IOException {
        List<TurnFishDTO> customDTOList = new ArrayList<>();

        for (Turn_fish turn_fish : turnFishDAO.getFishForTurnFishTBL(turnId) ) {
            Fish fish = fishDAO.search(turn_fish.getFishId());

            customDTOList.add(
                    new TurnFishDTO(
                            fish.getFishId(),
                            fish.getName(),
                            turn_fish.getWeight()
                    )
            );
        }
        return customDTOList;


    }

    @Override
    public FishDTO searchFish(String fishId) throws SQLException, IOException {
        Fish fish = fishDAO.search(fishId);

        return  new FishDTO(fish.getFishId() , fish.getName() , fish.getUnitPrice() , fish.getStock() );

    }

}
