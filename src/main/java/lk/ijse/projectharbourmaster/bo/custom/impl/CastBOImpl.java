package lk.ijse.projectharbourmaster.bo.custom.impl;

//import lk.ijse.projectharbourmaster.model.BoatModel;
//import lk.ijse.projectharbourmaster.model.CrewModel;
//import lk.ijse.projectharbourmaster.model.EmailModel;

import lk.ijse.projectharbourmaster.bo.BOFactory;
import lk.ijse.projectharbourmaster.bo.custom.CastBO;
import lk.ijse.projectharbourmaster.bo.custom.EmailBO;
import lk.ijse.projectharbourmaster.dao.DAOFactory;
import lk.ijse.projectharbourmaster.dao.custom.BoatDAO;
import lk.ijse.projectharbourmaster.dao.custom.CrewDAO;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CastBOImpl implements CastBO {
    BoatDAO boatDAO = (BoatDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOAT);
    CrewDAO crewDAO = (CrewDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CREW);
    EmailBO emailBO = (EmailBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMAIL);

    @Override
    public boolean cast(String selectedType , String message) throws SQLException {
        List<String> emailList = new ArrayList<>();

        if (selectedType.equals("Boat Owners")){
            emailList = boatDAO.getAllEmails();

        }else if (selectedType.equals("Crew Members")){
            emailList = crewDAO.getAllEmails();

        }

        for (int i = 0; i < emailList.size(); i++){
            try {
                emailBO.sendMail("projectharbourmaster001@gmail.com" , "voyglgayubzuirtf" , emailList.get(i) , message);

            } catch (MessagingException e) {
                System.out.println(emailList.get(i));
                e.printStackTrace();
            }

            if ( i == (emailList.size()-1) ){
                return true;
            }

        }
        return false;
    }
}
