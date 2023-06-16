package lk.ijse.projectharbourmaster.bo.custom.impl;

import lk.ijse.projectharbourmaster.bo.custom.MainMenuBO;
import lk.ijse.projectharbourmaster.dao.DAOFactory;
import lk.ijse.projectharbourmaster.dao.custom.*;
import lk.ijse.projectharbourmaster.dto.TurnDTO;
import lk.ijse.projectharbourmaster.entity.Turn;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainMenuBOImpl implements MainMenuBO {
    TurnDAO turnDAO = (TurnDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TURN);
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY_DAO);
    CrewDAO crewDAO = (CrewDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CREW);
    BoatDAO boatDAO = (BoatDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOAT);
    TurnCrewDAO turnCrewDAO = (TurnCrewDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TURN_CREW);
    TurnFishDAO turnFishDAO = (TurnFishDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TURN_FISH);
    FishDAO fishDAO = (FishDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.FISH);
    DockDAO dockDAO = (DockDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DOCK);
    WeatherDAO weatherDAO = (WeatherDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.WEATHER);

    @Override
    public List<String> getAllBoatsInSea() throws SQLException {
        return turnDAO.getAllBoatsInSea();

    }

    @Override
    public List<String> getAllCrewInSea() throws SQLException {
        return queryDAO.getAllCrewInSea();
    }

    @Override
    public List<TurnDTO> getAllInCompletedTurnsInDanger() throws SQLException {
        List<Turn> allInCompletedTurns = turnDAO.getAllInCompletedTurns();
        List<TurnDTO> allInCompletedTurnsInDanger = new ArrayList<>();

        for (Turn Turn : turnDAO.getAllInCompletedTurnsInDanger(allInCompletedTurns) ) {
            allInCompletedTurnsInDanger.add(
                    new TurnDTO(
                            Turn.getTurnId(),
                            Turn.getBoatId(),
                            Turn.getCapNIC(),
                            Turn.getCrewCount(),
                            Turn.getOutDate(),
                            Turn.getOutTime(),
                            Turn.getInDate(),
                            Turn.getInTime(),
                            null
                    )
            );
        }

        return allInCompletedTurnsInDanger;

    }

    @Override
    public int getAvailableCount(String dockId) throws SQLException {
        return dockDAO.getAvailableCount(dockId);

    }

    @Override
    public String searchWeatherSpecialCauses(String date) throws SQLException {
        return weatherDAO.searchWeatherSpecialCauses(date);

    }

}
