package lk.ijse.projectharbourmaster.model;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class  CastModel {
    public static boolean cast(String selectedType , String message) throws SQLException {
        List<String> emailList = new ArrayList<>();

        if (selectedType.equals("Boat Owners")){
            emailList = BoatModel.getAllEmails();

        }else if (selectedType.equals("Crew Members")){
            emailList = CrewModel.getAllEmails();

        }

        for (int i = 0; i < emailList.size(); i++){
            try {
                EmailModel.sendMail("projectharbourmaster001@gmail.com" , "voyglgayubzuirtf" , emailList.get(i) , message);

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
