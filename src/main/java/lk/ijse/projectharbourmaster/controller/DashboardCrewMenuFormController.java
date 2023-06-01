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
import lk.ijse.projectharbourmaster.dto.tm.CrewTM;
import lk.ijse.projectharbourmaster.model.CrewModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DashboardCrewMenuFormController {

    @FXML
    public JFXButton printReportBtn;

    @FXML
    private Pane crewMenuPane;

    @FXML
    private TableView<CrewTM> crewTbl;

    @FXML
    private TableColumn<?, ?> crewNicCol;

    @FXML
    private TableColumn<?, ?> crewNameCol;

    @FXML
    private TableColumn<?, ?> crewAddressCol;

    @FXML
    private TableColumn<?, ?> crewContactCol;

    @FXML
    private TableColumn<?, ?> crewAgeCol;

    @FXML
    private JFXButton crewAddCrewBtn;

    @FXML
    private JFXButton crewSearchBtn;

    @FXML
    private ComboBox<String> crewFilterComboBox;

    @FXML
    void initialize(){
        setCellValueFactory();

        try {
            loadCrewsTOTbl(CrewModel.getAllForTableFilter());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setComboBoxValues();

    }

    void setComboBoxValues(){
        crewFilterComboBox.getItems().addAll(
                "nic" ,
                "name"
        );

    }

    void setCellValueFactory() {
        crewNicCol.setCellValueFactory(new PropertyValueFactory<>("nic"));
        crewNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        crewAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        crewContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        crewAgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));
    }

    void loadCrewsTOTbl(List<CrewTM> crewTMList ){
            ObservableList<CrewTM> obList = FXCollections.observableArrayList();
            for (CrewTM row :crewTMList) {
                obList.add(row);
            }
            crewTbl.setItems(obList);
    }

    @FXML
    void crewAddCrewBtnOnAction(ActionEvent event) {
        DashboardFormController.fullScreen.getChildren().clear();
        try {
            DashboardFormController.fullScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/crew_add_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void crewFilterComboBoxOnAction(ActionEvent event) {
        try {
            loadCrewsTOTbl(CrewModel.getAllForTableFilter((" ORDER BY "+crewFilterComboBox.getValue())));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void crewSearchBtnOnAction(ActionEvent event) {
        DashboardFormController.fullScreen.getChildren().clear();
        try {
            DashboardFormController.fullScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/crew_search_form.fxml")));
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
                    JasperDesign load = JRXmlLoader.load(new File("C:\\Users\\DasunMadawa\\IdeaProjects\\project-harbourmaster\\src\\main\\resources\\js_report\\crew_all_data_form.jrxml"));

                    JasperReport js = JasperCompileManager.compileReport(load);

                    JasperPrint jp = JasperFillManager.fillReport(js, null, DBConnection.getInstance().getConnection());

                    JasperViewer viewer = new JasperViewer(jp , false);
                    viewer.show();

                } catch (JRException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                this.stop();
            }
        }.start();

    }

}
