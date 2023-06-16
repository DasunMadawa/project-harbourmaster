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
import lk.ijse.projectharbourmaster.bo.custom.FishBO;
import lk.ijse.projectharbourmaster.dto.FishDTO;
//import lk.ijse.projectharbourmaster.model.FIshModel;
import lk.ijse.projectharbourmaster.util.Validations;

import java.io.IOException;
import java.sql.SQLException;

public class FishAddFormController {

    @FXML
    public JFXTextField fishIdTxt;

    @FXML
    private JFXTextField fishNametxt;

    @FXML
    private JFXTextField pricetxt;

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton mainBtn;


    FishBO fishBO = (FishBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.FISH);


    @FXML
    void initialize(){
        setTextFieldValidations();

    }

    @FXML
    void addBtnOnAction(ActionEvent event) {
        if (!isAllDataValidated()){
            new Alert(Alert.AlertType.WARNING,
                    "Fill All Data Correctly",
                    ButtonType.OK
            ).show();
            return;

        }

        String fishIdTemp = fishIdTxt.getText();
        String fishName = fishNametxt.getText();
        double price = Double.parseDouble(pricetxt.getText());

        try {
            boolean isInserted = fishBO.addFish(new FishDTO(fishIdTemp , fishName , price , 0.0));

            if (isInserted){
                new Alert(Alert.AlertType.INFORMATION ,
                        "New Fish Added" ,
                        ButtonType.OK
                ).show();

                fishIdTxt.clear();
                fishNametxt.clear();
                pricetxt.clear();

            }else {
                new Alert(Alert.AlertType.ERROR ,
                        "Fish Adding Faild" ,
                        ButtonType.OK
                ).show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR ,
                    "Check Duplicate FishIds" ,
                    ButtonType.OK
            ).show();
        } catch (IOException e) {
            System.out.println(e);
        }


    }

    private void setTextFieldValidations() {
        Validations.setFocus(fishIdTxt, Validations.fishPattern);
        Validations.setFocus(fishNametxt, Validations.namePattern);
        Validations.setFocus(pricetxt, Validations.doublePattern42);

    }

    private boolean isAllDataValidated() {
        if (fishIdTxt.getFocusColor().equals(Paint.valueOf("red")) ||
                fishNametxt.getFocusColor().equals(Paint.valueOf("red")) ||
                pricetxt.getFocusColor().equals(Paint.valueOf("red"))
        ) {
            return false;
        }else if (fishIdTxt.getText().equals("") ||
                fishNametxt.getText().equals("") ||
                pricetxt.getText().equals("")

        ){
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
            DashboardFormController.menuScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_fish_menu_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void fishNametxtOnAction(ActionEvent event) {
        addBtnOnAction(event);
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
    void pricetxtOnAction(ActionEvent event) {
        addBtnOnAction(event);
    }

    @FXML
    public void fishIdTxtOnAction(ActionEvent actionEvent) {
        addBtnOnAction(actionEvent);
    }

}
