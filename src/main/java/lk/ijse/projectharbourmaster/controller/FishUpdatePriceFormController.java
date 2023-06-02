package lk.ijse.projectharbourmaster.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import lk.ijse.projectharbourmaster.dto.FishDTO;
import lk.ijse.projectharbourmaster.model.FIshModel;
import lk.ijse.projectharbourmaster.util.Validations;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FishUpdatePriceFormController {

    @FXML
    public ComboBox<String> fishIdComboBox;

    @FXML
    private JFXTextField fishNametxt;

    @FXML
    private JFXTextField pricetxt;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton mainBtn;

    private FishDTO fishDTO;

    @FXML
    void initialize(){
        setUpdatable(false);
        setComboBoxValue();
        setTextFieldValidations();

    }

    private void setTextFieldValidations() {
        Validations.setFocus(fishNametxt, Validations.namePattern);
        Validations.setFocus(pricetxt, Validations.doublePattern42);

    }

    private boolean isAllDataValidated() {
        if (fishNametxt.getFocusColor().equals(Paint.valueOf("red")) ||
                pricetxt.getFocusColor().equals(Paint.valueOf("red"))
        ) {
            return false;
        }
        return true;

    }

    private void setComboBoxValue() {
        try {
            List<FishDTO> allFishDTOS = FIshModel.getAllOrderBy("");

            ObservableList<String> fishIdObservableList = FXCollections.observableArrayList();

            for (FishDTO fishDTO : allFishDTOS) {
                fishIdObservableList.add(fishDTO.getFishId());

            }
            fishIdComboBox.setItems(fishIdObservableList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void setUpdatable(boolean updatable){
        if (updatable){
            fishNametxt.setEditable(true);
            pricetxt.setEditable(true);

            saveBtn.setVisible(true);

        }else {
            fishNametxt.setEditable(false);
            pricetxt.setEditable(false);

            saveBtn.setVisible(false);

        }

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

    }

    @FXML
    void saveBtnOnAction(ActionEvent event) {
        if (!isAllDataValidated()){
            new Alert(Alert.AlertType.WARNING,
                    "Fill All Data Correctly",
                    ButtonType.OK
            ).show();
            return;

        }

        fishDTO.setFishName(fishNametxt.getText());
        fishDTO.setUnitPrice(Double.parseDouble(pricetxt.getText()));

        boolean isUpdated = false;
        try {
            isUpdated = FIshModel.updateFishPrice(fishDTO);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (isUpdated){
            new Alert(Alert.AlertType.INFORMATION ,
                    "Fish Updated" ,
                    ButtonType.OK
            ).show();

            fishIdComboBox.setValue(null);
            fishNametxt.clear();
            pricetxt.clear();

        }else {
            new Alert(Alert.AlertType.INFORMATION ,
                    "Fish Is Not Updated" ,
                    ButtonType.OK
            ).show();

        }


    }

    public void fishIdComboBoxOnAction(ActionEvent actionEvent) {
        try {
            fishDTO = FIshModel.searchFish(fishIdComboBox.getValue()+"");

            if (fishDTO != null){
                fishNametxt.setText(fishDTO.getFishName());
                pricetxt.setText(fishDTO.getUnitPrice()+"");

                setUpdatable(true);
            }else {
                fishNametxt.clear();
                pricetxt.clear();

                setUpdatable(false);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
