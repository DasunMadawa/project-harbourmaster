package lk.ijse.projectharbourmaster.dao;

import lk.ijse.projectharbourmaster.dao.custom.impl.BoatDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static  DAOFactory getDaoFactory(){
        return daoFactory == null ?  daoFactory = new DAOFactory() : daoFactory;

    }

    public enum DAOTypes{
        Boat
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case Boat : return new BoatDAOImpl();
            default : return null;

        }

    }

}
