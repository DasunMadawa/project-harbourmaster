package lk.ijse.projectharbourmaster.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.tm.BoatTM;
import lk.ijse.projectharbourmaster.model.BoatModel;
import lk.ijse.projectharbourmaster.model.CrewModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DashboardBoatMenuFormController {

    @FXML
    public JFXButton printReportBtn;

    @FXML
    private Pane boatMenuPane;

    @FXML
    private TableView<BoatTM> boatTbl;

    @FXML
    private TableColumn<?, ?> boatBoatIdCol;

    @FXML
    private TableColumn<?, ?> boatBoatNameCol;

    @FXML
    private TableColumn<?, ?> boatBoatOwnerCol;

    @FXML
    private TableColumn<?, ?> boatBoatTypeCol;

    @FXML
    private TableColumn<?, ?> boatMaxCrewCol;

    @FXML
    private TableColumn<?, ?> boatFuelCapCol;

    @FXML
    private TableColumn<?, ?> boatWaterCapCol;

    @FXML
    private TableColumn<?, ?> boatMaxWeightCol;

    @FXML
    private TableColumn<?, ?> boatDockIdCol;

    @FXML
    private JFXButton boatAddBoatBtn;

    @FXML
    private JFXButton boatSearchBoatBtn;

    @FXML
    void initialize(){
        setCellValueFactory();
        try {
            loadBoatToTable(BoatModel.getAllWithFilter());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void setCellValueFactory() {
        boatBoatIdCol.setCellValueFactory(new PropertyValueFactory<>("boatId"));
        boatBoatOwnerCol.setCellValueFactory(new PropertyValueFactory<>("boatOwner"));
        boatBoatNameCol.setCellValueFactory(new PropertyValueFactory<>("boatName"));
        boatBoatTypeCol.setCellValueFactory(new PropertyValueFactory<>("boatType"));
        boatMaxCrewCol.setCellValueFactory(new PropertyValueFactory<>("noCrew"));
        boatFuelCapCol.setCellValueFactory(new PropertyValueFactory<>("fuelCap"));
        boatWaterCapCol.setCellValueFactory(new PropertyValueFactory<>("waterCap"));
        boatMaxWeightCol.setCellValueFactory(new PropertyValueFactory<>("maxWeight"));
        boatDockIdCol.setCellValueFactory(new PropertyValueFactory<>("dockId"));

    }

    private void loadBoatToTable(List<BoatTM> boatTMList) {
        ObservableList<BoatTM> obList = FXCollections.observableArrayList();
        for (BoatTM row : boatTMList) {
            obList.add(row);
        }
        boatTbl.setItems(obList);

    }

    @FXML
    void boatAddBoatBtnOnAction(ActionEvent event) {
        DashboardFormController.fullScreen.getChildren().clear();
        try {
            DashboardFormController.fullScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/boat_add_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void boatSearchBoatBtnOnAction(ActionEvent event) {
        DashboardFormController.fullScreen.getChildren().clear();
        try {
            DashboardFormController.fullScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/boat_search_form.fxml")));
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
                    JasperDesign load = JRXmlLoader.load(new File("C:\\Users\\DasunMadawa\\IdeaProjects\\project-harbourmaster\\src\\main\\resources\\js_report\\boat_all_data_form.jrxml"));

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
