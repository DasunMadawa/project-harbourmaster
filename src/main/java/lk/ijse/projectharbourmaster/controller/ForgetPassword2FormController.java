package lk.ijse.projectharbourmaster.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lk.ijse.projectharbourmaster.dto.UserDTO;
import lk.ijse.projectharbourmaster.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

public class ForgetPassword2FormController {

    @FXML
    private JFXTextField userNameTxt;

    @FXML
    private JFXButton changeBtn;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXPasswordField passwordFieldConfirm;

    @FXML
    void changeBtnOnAction(ActionEvent event) {
        if (!((userNameTxt.getText().length() | passwordField.getText().length() | passwordFieldConfirm.getText().length()) == 0 )){
            String userId = "U001";
            String userName = userNameTxt.getText();
            String password = passwordField.getText();

            if (!passwordField.getText().equals(passwordFieldConfirm.getText())){
                new Alert(Alert.AlertType.ERROR ,
                        "Password Confirm Not Matches" ,
                        ButtonType.OK
                ).show();
                return;

            }

            try {
                if (UserModel.updateUser(new UserDTO(userId , null , userName , password))){
                    new Alert(Alert.AlertType.INFORMATION ,
                            "Password changed" ,
                            ButtonType.OK
                    ).show();

                    LoginFormController.rootcopy.getChildren().clear();
                    try {
                        LoginFormController.rootcopy.getChildren().add(FXMLLoader.load(getClass().getResource("/view/login_form.fxml")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else{
                    new Alert(Alert.AlertType.INFORMATION ,
                            "Password not changed" ,
                            ButtonType.OK
                    ).show();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR ,
                        "Check Duplicate User Name" ,
                        ButtonType.OK
                ).show();
            }

        }else {
            new Alert(Alert.AlertType.ERROR ,
                    "Fill The All Fields" ,
                    ButtonType.OK
            ).show();
        }
    }

    @FXML
    void passwordFieldConfirmOnAction(ActionEvent event) {
        changeBtnOnAction(event);
    }

    @FXML
    void passwordFieldOnAction(ActionEvent event) {
        changeBtnOnAction(event);
    }

    @FXML
    void userNameTxtOnAction(ActionEvent event) {
        changeBtnOnAction(event);
    }

}
