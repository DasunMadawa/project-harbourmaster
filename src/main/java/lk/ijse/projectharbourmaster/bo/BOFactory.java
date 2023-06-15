package lk.ijse.projectharbourmaster.bo;

import lk.ijse.projectharbourmaster.bo.custom.impl.BoatBOImpl;
import lk.ijse.projectharbourmaster.bo.custom.impl.CastBOImpl;
import lk.ijse.projectharbourmaster.bo.custom.impl.CrewBOImpl;
import lk.ijse.projectharbourmaster.bo.custom.impl.EmailBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBoFactory(){
        return boFactory == null ?  boFactory = new BOFactory() : boFactory;

    }

    public enum BOTypes{
        BOAT , CREW , CAST , EMAIL
    }

    public SuperBO getBO(BOTypes daoTypes){
        switch (daoTypes){
            case BOAT : return new BoatBOImpl();
            case CREW : return new CrewBOImpl();
            case CAST : return new CastBOImpl();
            case EMAIL : return new EmailBOImpl();
            default : return null;

        }

    }

}
