package lk.ijse.projectharbourmaster.dao;

import lk.ijse.projectharbourmaster.bo.custom.impl.CastBOImpl;
import lk.ijse.projectharbourmaster.dao.custom.impl.BoatDAOImpl;
import lk.ijse.projectharbourmaster.dao.custom.impl.CrewDAOImpl;
import lk.ijse.projectharbourmaster.dao.custom.impl.QueryDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static  DAOFactory getDaoFactory(){
        return daoFactory == null ?  daoFactory = new DAOFactory() : daoFactory;

    }

    public enum DAOTypes{
        BOAT , CREW , QUERY_DAO
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case BOAT : return new BoatDAOImpl();
            case CREW : return new CrewDAOImpl();
            case QUERY_DAO : return new QueryDAOImpl();
            default : return null;

        }

    }

}
