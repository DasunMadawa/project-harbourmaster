package lk.ijse.projectharbourmaster.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.projectharbourmaster.bo.BOFactory;
import lk.ijse.projectharbourmaster.bo.custom.UserBO;
import lk.ijse.projectharbourmaster.dto.UserDTO;
//import lk.ijse.projectharbourmaster.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    public AnchorPane root;

    @FXML
    private JFXTextField userNameTxt;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXPasswordField passwordField;

    public static AnchorPane rootcopy;

    public static String userId;


    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);


    @FXML
    public void initialize(){
        rootcopy = root;
    }

    @FXML
    void forgetPasswordOnMouseClicked(MouseEvent event) {
        root.getChildren().clear();
        try {
            root.getChildren().add(FXMLLoader.load(getClass().getResource("/view/forget_password_1_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loginBtnOnAction(ActionEvent event) {
        try {
            UserDTO userDTO = userBO.searchUserByUserName(userNameTxt.getText());
            if (userDTO != null && userDTO.getPassword().equals(passwordField.getText())){
                this.userId = userDTO.getUserId();
                root.getChildren().clear();
                try {
                    root.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                new Alert(Alert.AlertType.ERROR ,
                        "UserName or Password Error" ,
                        ButtonType.OK
                ).show();

                userNameTxt.setText("");
                passwordField.setText("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR ,
                    "Sql Error" ,
                    ButtonType.OK
            ).show();

            userNameTxt.setText("");
            passwordField.setText("");
        }


    }

    @FXML
    void passwordFieldOnAction(ActionEvent event) {
        loginBtnOnAction(new ActionEvent());
    }

    @FXML
    void userNameTxtOnAction(ActionEvent event) {
        loginBtnOnAction(new ActionEvent());
    }

}
