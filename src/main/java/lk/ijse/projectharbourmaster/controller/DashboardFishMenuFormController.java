package lk.ijse.projectharbourmaster.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.FishDTO;
import lk.ijse.projectharbourmaster.model.FIshModel;
import lk.ijse.projectharbourmaster.util.Validations;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DashboardFishMenuFormController {

    @FXML
    public JFXButton printReportBtn;

    @FXML
    private Pane fishMenuPane;

    @FXML
    private TableView<FishDTO> fishTbl;

    @FXML
    private TableColumn<?, ?> fishFishIdCol;

    @FXML
    private TableColumn<?, ?> fishFishNameCol;

    @FXML
    private TableColumn<?, ?> fishUnitPriceCol;

    @FXML
    private TableColumn<?, ?> fishStockCol;

    @FXML
    private JFXButton fishAddFishBtn;

    @FXML
    private JFXButton removeFishBtn;

    @FXML
    private JFXTextField fishRemoveFishTxt;

    @FXML
    private TableView<FishDTO> fishHighesRatesTbl;

    @FXML
    private TableColumn<?, ?> fishFishIdCol1;

    @FXML
    private TableColumn<?, ?> fishFishNameCol1;

    @FXML
    private TableColumn<?, ?> fishUnitPriceCol1;

    @FXML
    private TableColumn<?, ?> fishStockCol1;

    @FXML
    private TableView<FishDTO> fishHighestStockTbl;

    @FXML
    private TableColumn<?, ?> fishFishIdCol11;

    @FXML
    private TableColumn<?, ?> fishFishNameCol11;

    @FXML
    private TableColumn<?, ?> fishUnitPriceCol11;

    @FXML
    private TableColumn<?, ?> fishStockCol11;

    @FXML
    private JFXButton updatePriceBtn;

    @FXML
    void initialize(){
        setCellValueFactory();
        setTables();
        setTextFieldValidations();
    }

    private void setTextFieldValidations() {
        Validations.setFocus(fishRemoveFishTxt, Validations.fishPattern);

    }

    private boolean isAllDataValidated() {
        if ( fishRemoveFishTxt.getFocusColor().equals(Paint.valueOf("red")) ) {
            return false;
        }
        return true;

    }

    private void setTables() {
        try {
            List<FishDTO> fishDTOTable = FIshModel.getAllOrderBy("");
            List<FishDTO> fishDTOHighestRatesTable = FIshModel.getAllOrderBy(" ORDER BY unitPrice DESC LIMIT 5");
            List<FishDTO> fishDTOHighestStockTable = FIshModel.getAllOrderBy(" ORDER BY stock DESC LIMIT 5");

            ObservableList fishTableOblist = FXCollections.observableArrayList();
            ObservableList fishHighestRatesTableOblist = FXCollections.observableArrayList();
            ObservableList fishHighestStockTableOblist = FXCollections.observableArrayList();

            for (FishDTO fishDTO : fishDTOTable) {
                fishTableOblist.add(fishDTO);
            }

            for (FishDTO fishDTO : fishDTOHighestRatesTable) {
                fishHighestRatesTableOblist.add(fishDTO);
            }

            for (FishDTO fishDTO : fishDTOHighestStockTable) {
                fishHighestStockTableOblist.add(fishDTO);
            }

            fishTbl.setItems(fishTableOblist);
            fishHighesRatesTbl.setItems(fishHighestRatesTableOblist);
            fishHighestStockTbl.setItems(fishHighestStockTableOblist);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    void setCellValueFactory() {
        fishFishIdCol.setCellValueFactory(new PropertyValueFactory<>("fishId"));
        fishFishNameCol.setCellValueFactory(new PropertyValueFactory<>("fishName"));
        fishUnitPriceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        fishStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        fishFishIdCol1.setCellValueFactory(new PropertyValueFactory<>("fishId"));
        fishFishNameCol1.setCellValueFactory(new PropertyValueFactory<>("fishName"));
        fishUnitPriceCol1.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        fishStockCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));

        fishFishIdCol11.setCellValueFactory(new PropertyValueFactory<>("fishId"));
        fishFishNameCol11.setCellValueFactory(new PropertyValueFactory<>("fishName"));
        fishUnitPriceCol11.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        fishStockCol11.setCellValueFactory(new PropertyValueFactory<>("stock"));


    }

    @FXML
    void fishAddFishBtnOnAction(ActionEvent event) {
        DashboardFormController.fullScreen.getChildren().clear();
        try {
            DashboardFormController.fullScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/fish_add_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void fishRemoveFishTxtOnAction(ActionEvent event) {
        removeFishBtnOnAction(event);
    }

    @FXML
    void removeFishBtnOnAction(ActionEvent event) {
        if (!isAllDataValidated()){
            new Alert(Alert.AlertType.WARNING,
                    "Enter DockId Correctly",
                    ButtonType.OK
            ).show();
            return;

        }

        if (fishRemoveFishTxt.getText() == null || fishRemoveFishTxt.equals("")){
            new Alert(Alert.AlertType.INFORMATION ,
                    "Enter FishId" ,
                    ButtonType.OK
            ).show();
            return;
        }

        try {
            FishDTO fishDTO = FIshModel.searchFish(fishRemoveFishTxt.getText());

            if (fishDTO != null){
                boolean isDroped = FIshModel.drop(fishDTO);

                if (isDroped) {
                    new Alert(Alert.AlertType.INFORMATION,
                            "Fish Deleted",
                            ButtonType.OK
                    ).show();

                    fishRemoveFishTxt.clear();
                    initialize();
                }

            }else {
                new Alert(Alert.AlertType.INFORMATION ,
                        "Enter Correct FishId" ,
                        ButtonType.OK
                ).show();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void updatePriceBtnOnAction(ActionEvent event) {
        DashboardFormController.fullScreen.getChildren().clear();
        try {
            DashboardFormController.fullScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/fish_update_price_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void printReportBtnOnAction(ActionEvent actionEvent) {
        new Thread() {
            @Override
            public void run() {
                try {
                    JasperDesign load = JRXmlLoader.load(new File("C:\\Users\\DasunMadawa\\IdeaProjects\\project-harbourmaster\\src\\main\\resources\\js_report\\fish_all_data_form.jrxml"));

                    JasperReport js = JasperCompileManager.compileReport(load);

                    JasperPrint jp = JasperFillManager.fillReport(js, null, DBConnection.getInstance().getConnection());

                    JasperViewer viewer = new JasperViewer(jp , false);
                    viewer.show();

                } catch (
                        JRException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                this.stop();
            }
        }.start();

    }

}
