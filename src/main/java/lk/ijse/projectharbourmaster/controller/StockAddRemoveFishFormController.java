package lk.ijse.projectharbourmaster.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import lk.ijse.projectharbourmaster.dto.FishDTO;
import lk.ijse.projectharbourmaster.dto.StockUpdateDTO;
import lk.ijse.projectharbourmaster.model.FIshModel;
import lk.ijse.projectharbourmaster.model.StockFishModel;
import lk.ijse.projectharbourmaster.util.Validations;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StockAddRemoveFishFormController {

    @FXML
    private JFXButton updateBtn;

    @FXML
    private Label fishNameLabel;

    @FXML
    private ComboBox<String> addOrRemoveComboBox;

    @FXML
    private ComboBox<String> fishIdComboBox;

    @FXML
    private ComboBox<String> stockIdComboBox;

    @FXML
    private Label currentStockLbl;

    @FXML
    private JFXTextField addingWeighttxt;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton mainBtn;

    private FishDTO fishDTO;

    @FXML void initialize(){
        setFishIds();
        setComboBoxValues();
        setTextFieldValidations();

    }

    private void setTextFieldValidations() {
        Validations.setFocus(addingWeighttxt, Validations.doublePattern22);

    }

    private boolean isAllDataValidated() {
        if ( addingWeighttxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red")) ||
                addingWeighttxt.getText().equals("") ||
                fishIdComboBox.getValue() == null ||
                stockIdComboBox.getValue() == null ||
                addOrRemoveComboBox.getValue() == null

        ) {
            return false;
        }
        return true;

    }



    private void setComboBoxValues() {
        addOrRemoveComboBox.getItems().addAll(
                "Add" ,
                "Remove"
        );

        stockIdComboBox.getItems().addAll(
                "S1"
        );

    }

    private void setFishIds() {
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

    @FXML
    void updateBtnOnAction(ActionEvent event) {
        if (!isAllDataValidated()){
            new Alert(Alert.AlertType.WARNING,
                    "Fill All Data Correctly",
                    ButtonType.OK
            ).show();
            return;

        }

        boolean add = false;
        if (addOrRemoveComboBox.getValue().equals("Add")){
            add = true;
        }

        StockUpdateDTO stockUpdateDTO = new StockUpdateDTO(stockIdComboBox.getValue() , Double.valueOf(addingWeighttxt.getText()) , add , fishDTO);

        boolean isUpdated = StockFishModel.addStock(stockUpdateDTO);
        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION ,
                    "Stock Updated" ,
                    ButtonType.OK
            ).show();

            fishNameLabel.setText("");
            currentStockLbl.setText("");

            fishIdComboBox.setValue("Fish Id");
            addOrRemoveComboBox.setValue("Select Type");
            stockIdComboBox.setValue("Stock Id");

            addingWeighttxt.clear();

        }else {
            new Alert(Alert.AlertType.ERROR ,
                    "Stock Not Updated Check Stocks" ,
                    ButtonType.OK
            ).show();
        }

    }

    @FXML
    void addOrRemoveComboBoxOnAction(ActionEvent event) {

    }

    @FXML
    void addingWeighttxtOnAction(ActionEvent event) {
        updateBtnOnAction(event);
    }

    @FXML
    void backBtnOnAction(ActionEvent event) {
        DashboardFormController.fullScreen.getChildren().clear();
        DashboardFormController.menuScreen.getChildren().clear();
        try {
            DashboardFormController.fullScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml")));
            DashboardFormController.menuScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_stock_menu_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void fishIdComboBoxOnAction(ActionEvent event) {
        try {
            fishDTO = FIshModel.searchFish(fishIdComboBox.getValue());

            if (fishDTO != null){
                fishNameLabel.setText(fishDTO.getFishName());
                currentStockLbl.setText(fishDTO.getStock() + "");

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
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
    void stockIdComboBoxOnAction(ActionEvent event) {

    }

}
