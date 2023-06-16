package lk.ijse.projectharbourmaster.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lk.ijse.projectharbourmaster.util.EmailUtil;
//import lk.ijse.projectharbourmaster.model.EmailModel;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Random;

public class ForgetPassword1FormController {

    @FXML
    private JFXTextField otpTxt;

    @FXML
    private JFXButton recoverBtn;

    private int randomOtp;

    @FXML
    public void initialize(){
        randomOtp = new Random().nextInt(1000000);

        new Thread() {
            @Override
            public void run() {
                sendOtp(randomOtp+"");
                this.stop();
            }
        }.start();
        System.out.println(randomOtp);
    }

    public void sendOtp(String message){
        try {
            EmailUtil.sendMail("projectharbourmaster001@gmail.com" , "voyglgayubzuirtf" , "projectharbourmaster001@gmail.com" , message);
        } catch (MessagingException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR ,
                    "UserName or Password Error" ,
                    ButtonType.OK
            ).show();
        }
    }

    @FXML
    void otpTxtOnAction(ActionEvent event) {
        recoverBtnOnAction(new ActionEvent());
    }

    @FXML
    void recoverBtnOnAction(ActionEvent event) {
        if (otpTxt.getText().equals((randomOtp+""))){
            LoginFormController.rootcopy.getChildren().clear();
            try {
                LoginFormController.rootcopy.getChildren().add(FXMLLoader.load(getClass().getResource("/view/forget_password_2_form.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            new Alert(Alert.AlertType.ERROR ,
                    "Invalid Otp" ,
                    ButtonType.OK
            ).show();
            otpTxt.setText("");
        }

    }

}
