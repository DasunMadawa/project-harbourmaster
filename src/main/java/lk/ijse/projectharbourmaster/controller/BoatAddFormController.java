package lk.ijse.projectharbourmaster.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Paint;
import lk.ijse.projectharbourmaster.bo.BOFactory;
import lk.ijse.projectharbourmaster.bo.custom.BoatBO;
import lk.ijse.projectharbourmaster.dto.BoatDTO;
//import lk.ijse.projectharbourmaster.model.BoatModel;
import lk.ijse.projectharbourmaster.util.Validations;

import java.io.IOException;
import java.sql.SQLException;

public class BoatAddFormController {

    @FXML
    private JFXTextField boatIdtxt;

    @FXML
    private JFXTextField boatNametxt;

    @FXML
    private JFXTextField boatTypetxt;

    @FXML
    private JFXTextField noCrewtxt;

    @FXML
    private JFXTextField fuelCaptxt;

    @FXML
    private JFXTextField waterCaptxt;

    @FXML
    private JFXTextField maxWeighttxt;

    @FXML
    private JFXTextField boatOwnertxt;

    @FXML
    private JFXTextField emailtxt;

    @FXML
    private JFXButton registerBtn;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton mainBtn;

    BoatBO boatBO = (BoatBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BOAT);

    @FXML
    void initialize() {
        setTextFieldValidations();

    }

    private void setTextFieldValidations() {
        Validations.setFocus(boatIdtxt, Validations.boatPattern);
        Validations.setFocus(boatNametxt, Validations.namePattern);
        Validations.setFocus(boatTypetxt, Validations.boatTypePattern);
        Validations.setFocus(noCrewtxt, Validations.intPattern2);
        Validations.setFocus(fuelCaptxt, Validations.doublePattern22);
        Validations.setFocus(waterCaptxt, Validations.doublePattern22);
        Validations.setFocus(boatOwnertxt, Validations.namePattern);
        Validations.setFocus(emailtxt, Validations.emailPattern);
        Validations.setFocus(maxWeighttxt, Validations.doublePattern22);

    }

    private boolean isAllDataValidated() {
        if (boatIdtxt.getFocusColor().equals(Paint.valueOf("red")) ||
                boatNametxt.getFocusColor().equals(Paint.valueOf("red")) ||
                boatTypetxt.getFocusColor().equals(Paint.valueOf("red")) ||
                noCrewtxt.getFocusColor().equals(Paint.valueOf("red")) ||
                fuelCaptxt.getFocusColor().equals(Paint.valueOf("red")) ||
                waterCaptxt.getFocusColor().equals(Paint.valueOf("red")) ||
                boatOwnertxt.getFocusColor().equals(Paint.valueOf("red")) ||
                emailtxt.getFocusColor().equals(Paint.valueOf("red")) ||
                maxWeighttxt.getFocusColor().equals(Paint.valueOf("red"))
        ) {
            return false;
        } else if (boatIdtxt.getText().equals("") ||
                boatNametxt.getText().equals("") ||
                boatTypetxt.getText().equals("") ||
                noCrewtxt.getText().equals("") ||
                fuelCaptxt.getText().equals("") ||
                waterCaptxt.getText().equals("") ||
                boatOwnertxt.getText().equals("") ||
                emailtxt.getText().equals("") ||
                maxWeighttxt.getText().equals("")

        ) {
            return false;
        }
        return true;

    }

    @FXML
    void backBtnOnAction(ActionEvent event) {
        DashboardFormController.fullScreen.getChildren().clear();
        DashboardFormController.menuScreen.getChildren().clear();
        try {
            DashboardFormController.fullScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml")));
            DashboardFormController.menuScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_boat_menu_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void boatIdtxtOnAction(ActionEvent event) {
        registerBtnOnAction(new ActionEvent());
    }

    @FXML
    void boatNametxtOnAction(ActionEvent event) {
        registerBtnOnAction(new ActionEvent());
    }

    @FXML
    void boatOwnertxtOnAction(ActionEvent event) {
        registerBtnOnAction(new ActionEvent());
    }

    @FXML
    void boatTypetxtOnAction(ActionEvent event) {
        registerBtnOnAction(new ActionEvent());
    }

    @FXML
    void emailtxtOnAction(ActionEvent event) {
        registerBtnOnAction(new ActionEvent());
    }

    @FXML
    void fuelCaptxtOnAction(ActionEvent event) {
        registerBtnOnAction(new ActionEvent());
    }

    @FXML
    void mainBtnOnAction(ActionEvent event) {
        DashboardFormController.fullScreen.getChildren().clear();
        try {
            DashboardFormController.fullScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void maxWeighttxtOnAction(ActionEvent event) {
        registerBtnOnAction(new ActionEvent());
    }

    @FXML
    void noCrewtxtOnAction(ActionEvent event) {
        registerBtnOnAction(new ActionEvent());
    }

    @FXML
    void registerBtnOnAction(ActionEvent event) {
        if (!isAllDataValidated()) {
            new Alert(Alert.AlertType.WARNING,
                    "Fill All Data Correctly",
                    ButtonType.OK
            ).show();
            return;

        }


        String boatId = boatIdtxt.getText();
        String boatOwner = boatOwnertxt.getText();
        String boatName = boatNametxt.getText();
        String boatType = boatTypetxt.getText();
        int noCrew = Integer.parseInt(noCrewtxt.getText());
        double fuelCap = Double.parseDouble(fuelCaptxt.getText());
        double waterCap = Double.parseDouble(waterCaptxt.getText());
        double maxWeight = Double.parseDouble(maxWeighttxt.getText());
        String email = emailtxt.getText();

        try {
            boolean isInserted = boatBO.addBoat(new BoatDTO(boatId, boatOwner, boatName, boatType, noCrew, fuelCap, waterCap, maxWeight, email));

            if (isInserted) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Data Inserted",
                        ButtonType.OK
                ).show();

                boatIdtxt.clear();
                boatOwnertxt.clear();
                boatNametxt.clear();
                boatTypetxt.clear();
                noCrewtxt.clear();
                fuelCaptxt.clear();
                waterCaptxt.clear();
                maxWeighttxt.clear();
                emailtxt.clear();

            } else {
                new Alert(Alert.AlertType.INFORMATION,
                        "Data Not Inserted Check duplicate Data",
                        ButtonType.OK
                ).show();
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    @FXML
    void waterCaptxtOnAction(ActionEvent event) {
        registerBtnOnAction(new ActionEvent());
    }

}
