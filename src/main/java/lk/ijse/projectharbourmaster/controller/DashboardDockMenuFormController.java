package lk.ijse.projectharbourmaster.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import lk.ijse.projectharbourmaster.dto.tm.BoatTM;
import lk.ijse.projectharbourmaster.dto.tm.DockTM;
import lk.ijse.projectharbourmaster.model.BoatModel;
import lk.ijse.projectharbourmaster.model.DockModel;
import lk.ijse.projectharbourmaster.util.Validations;

import java.sql.SQLException;
import java.util.List;

public class DashboardDockMenuFormController {

    @FXML
    public JFXTextField boatIdSearchTxt;

    @FXML
    private Pane dockMenuPane;

    @FXML
    private JFXButton dockBoatBtn;

    @FXML
    private JFXButton undockBtn;

    @FXML
    private JFXTextField dockDockIdText;

    @FXML
    private TableView<DockTM> dockTbl;

    @FXML
    private TableColumn<?, ?> dockMenuDockIdCol;

    @FXML
    private TableColumn<?, ?> dockMenuBoatIdCol;

    @FXML
    private TableColumn<?, ?> dockMenuInDateCol;

    @FXML
    private ComboBox<String> dockDockBoatIdComboBox;

    @FXML
    private ComboBox<String> dockUndockBoatIdComboBox;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private Label dockIdLbl;

    @FXML
    private Label dateLbl;

    @FXML
    private Label D1111;

    @FXML
    private Label D1121;

    @FXML
    private Label D1211;

    @FXML
    private Label D1221;

    @FXML
    private Label D1311;

    @FXML
    private Label D1321;

    @FXML
    private Label D3111;

    @FXML
    private Label D3121;

    @FXML
    private Label D3211;

    @FXML
    private Label D3221;

    @FXML
    private Label D3311;

    @FXML
    private Label D3321;

    @FXML
    private Label D2111;

    @FXML
    private Label D2121;

    @FXML
    private Label D2211;

    @FXML
    private Label D2221;

    @FXML
    private Label D2311;

    @FXML
    private Label D2321;

    @FXML
    void initialize(){
        setAvailableDocks();
        setComboBoxValues();
        setCellValueFactory();
        setColDetails();
        setTextFieldValidations();

    }


    private void setTextFieldValidations() {
        Validations.setFocus(boatIdSearchTxt, Validations.boatPattern);
        Validations.setFocus(dockDockIdText, Validations.dockPattern);

    }

    private boolean isAllDataValidated() {
        if ( dockDockIdText.getFocusColor().equals(Paint.valueOf("red")) ) {
            return false;
        }
        return true;

    }

    void setColDetails(){
        try {
            List<DockTM> all = DockModel.getAll();
            ObservableList<DockTM> obList = FXCollections.observableArrayList();

            for (DockTM dockTM :all) {
                obList.add(dockTM);
            }
            dockTbl.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void setCellValueFactory() {
        dockMenuBoatIdCol.setCellValueFactory(new PropertyValueFactory<>("boatId"));
        dockMenuDockIdCol.setCellValueFactory(new PropertyValueFactory<>("dockId"));
        dockMenuInDateCol.setCellValueFactory(new PropertyValueFactory<>("inDate"));


    }

    public void setAvailableDocks() {
        try {
            D1111.setText(DockModel.getAvailableCount("D111")+"");
            D1121.setText(DockModel.getAvailableCount("D112")+"");
            D1211.setText(DockModel.getAvailableCount("D121")+"");
            D1221.setText(DockModel.getAvailableCount("D122")+"");
            D1311.setText(DockModel.getAvailableCount("D131")+"");
            D1321.setText(DockModel.getAvailableCount("D132")+"");
            D2111.setText(DockModel.getAvailableCount("D211")+"");
            D2121.setText(DockModel.getAvailableCount("D212")+"");
            D2211.setText(DockModel.getAvailableCount("D221")+"");
            D2221.setText(DockModel.getAvailableCount("D222")+"");
            D2311.setText(DockModel.getAvailableCount("D231")+"");
            D2321.setText(DockModel.getAvailableCount("D232")+"");
            D3111.setText(DockModel.getAvailableCount("D311")+"");
            D3121.setText(DockModel.getAvailableCount("D312")+"");
            D3211.setText(DockModel.getAvailableCount("D321")+"");
            D3221.setText(DockModel.getAvailableCount("D322")+"");
            D3311.setText(DockModel.getAvailableCount("D331")+"");
            D3321.setText(DockModel.getAvailableCount("D332")+"");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void dockBoatBtnOnAction(ActionEvent event) {
        if (!isAllDataValidated()){
            new Alert(Alert.AlertType.WARNING,
                    "Enter DockId Correctly",
                    ButtonType.OK
            ).show();
            return;

        }

        if (dockDockBoatIdComboBox.getValue() == null || dockDockIdText.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,
                    "Enter DockId and Boat Correctly",
                    ButtonType.OK
            ).show();
            return;
        }

        try {
            System.out.println(DockModel.getAvailableCount(dockDockIdText.getText()));
            System.out.println(DockModel.getAvailableCount(dockDockIdText.getText()) > 0);

            if (DockModel.getAvailableCount(dockDockIdText.getText()) > 0 ) {
                boolean isDocked = DockModel.dockBoats(dockDockIdText.getText(), dockDockBoatIdComboBox.getValue());
                if (isDocked) {
                    new Alert(Alert.AlertType.CONFIRMATION,
                            "Boat Successfuly Docked",
                            ButtonType.OK
                    ).show();
                    initialize();
                } else {
                    new Alert(Alert.AlertType.CONFIRMATION,
                            "Boat Not Docked Check Is Already Docked",
                            ButtonType.OK
                    ).show();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void dockDockBoatIdComboBoxOnAction(ActionEvent event) {

    }

    @FXML
    void dockDockIdTextOnAction(ActionEvent event) {
        setComboBoxValues();
    }

    private void setComboBoxValues() {
        try {
            List <String> boatIdsDock = BoatModel.selectAll();
            List <String> boatIdsUndock = DockModel.getBoatIdForUndock(dockDockIdText.getText());

            ObservableList<String> obListDock = FXCollections.observableArrayList();
            ObservableList<String> obListUnDock = FXCollections.observableArrayList();


            for (String boatId : boatIdsDock) {
                boolean isOk = DockModel.getBoatIdForDock(boatId);
                if (isOk) {
                    obListDock.add(boatId);
                }
            }

            for (String boatId : boatIdsUndock){
                obListUnDock.add(boatId);
            }

            dockDockBoatIdComboBox.setItems(obListDock);
            dockUndockBoatIdComboBox.setItems(obListUnDock);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void dockUndockBoatIdComboBoxOnAction(ActionEvent event) {

    }

    @FXML
    void searchBtnOnAction(ActionEvent event) {
        try {
            DockTM dockTM = DockModel.searchBoat(boatIdSearchTxt.getText());

            if (dockTM == null){
                new Alert(Alert.AlertType.CONFIRMATION,
                        "Ivalid Dock Id",
                        ButtonType.OK
                ).show();
                return;
            }

            dockIdLbl.setText(dockTM.getDockId());
            dateLbl.setText(dockTM.getInDate());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void undockBtnOnAction(ActionEvent event) {
        if (!isAllDataValidated()){
            new Alert(Alert.AlertType.WARNING,
                    "Enter DockId Correctly",
                    ButtonType.OK
            ).show();
            return;

        }

        if (dockUndockBoatIdComboBox.getValue() == null || dockDockIdText.getText().equals("")){
            new Alert(Alert.AlertType.WARNING,
                    "Enter DockId and Boat Correctly",
                    ButtonType.OK
            ).show();
            return;
        }

        try {
            boolean isUnDocked = DockModel.undockBoats(dockDockIdText.getText(), dockUndockBoatIdComboBox.getValue());
            if (isUnDocked) {
                new Alert(Alert.AlertType.CONFIRMATION,
                        "Boat Successfuly Undocked",
                        ButtonType.OK
                ).show();

                initialize();

            } else {
                new Alert(Alert.AlertType.CONFIRMATION,
                        "Not Undocked",
                        ButtonType.OK
                ).show();


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void boatIdSearchTxtOnAction(ActionEvent actionEvent) {
        searchBtnOnAction(new ActionEvent());
    }

}
