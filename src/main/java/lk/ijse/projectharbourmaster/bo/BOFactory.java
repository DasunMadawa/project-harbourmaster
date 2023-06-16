package lk.ijse.projectharbourmaster.bo;

import lk.ijse.projectharbourmaster.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBoFactory(){
        return boFactory == null ?  boFactory = new BOFactory() : boFactory;

    }

    public enum BOTypes{
        BOAT , CREW , CAST , DOCK , FISH , OFFICE , STOCK , TURN , USER , WEATHER , MAIN_MENU
    }

    public SuperBO getBO(BOTypes daoTypes){
        switch (daoTypes){
            case BOAT : return new BoatBOImpl();
            case CREW : return new CrewBOImpl();
            case CAST : return new CastBOImpl();
            case DOCK : return new DockBOImpl();
            case FISH : return new FishBOImpl();
            case OFFICE : return new OfficeBOImpl();
            case STOCK : return new StockBOImpl();
            case TURN : return new TurnBOImpl();
            case USER : return new UserBOImpl();
            case WEATHER : return new WeatherBOImpl();
            case MAIN_MENU : return new MainMenuBOImpl();
            default : return null;

        }

    }

}
