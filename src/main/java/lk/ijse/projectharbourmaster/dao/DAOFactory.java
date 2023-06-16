package lk.ijse.projectharbourmaster.dao;

import lk.ijse.projectharbourmaster.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static  DAOFactory getDaoFactory(){
        return daoFactory == null ?  daoFactory = new DAOFactory() : daoFactory;

    }

    public enum DAOTypes{
        BOAT , CREW , DOCK , FISH , OFFICE , STOCK , STOCK_FISH , TURN , TURN_CREW , TURN_FISH , USER , WEATHER , WEATHER_EMAIL , QUERY_DAO
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case BOAT : return new BoatDAOImpl();
            case CREW : return new CrewDAOImpl();
            case DOCK : return new DockDAOImpl();
            case FISH : return new FishDAOImpl();
            case OFFICE : return new OfficeDAOImpl();
            case STOCK : return new StockDAOImpl();
            case STOCK_FISH : return new StockFishDAOImpl();
            case TURN : return new TurnDAOImpl();
            case TURN_CREW : return new TurnCrewDAOImpl();
            case TURN_FISH : return new TurnFishDAOImpl();
            case USER : return new UserDAOImpl();
            case WEATHER : return new WeatherDAOImpl();
            case WEATHER_EMAIL : return new WeatherEmailDAOImpl();
            case QUERY_DAO : return new QueryDAOImpl();
            default : return null;

        }

    }

}
