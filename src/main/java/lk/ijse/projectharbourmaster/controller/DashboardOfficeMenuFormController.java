package lk.ijse.projectharbourmaster.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.tm.OfficeTM;
import lk.ijse.projectharbourmaster.model.OfficeModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DashboardOfficeMenuFormController {

    @FXML
    public JFXButton printReportBtn;

    @FXML
    private Pane officeMenuPane;

    @FXML
    private TableView<OfficeTM> officeTbl;

    @FXML
    private TableColumn<?, ?> officeNicCol;

    @FXML
    private TableColumn<?, ?> officeNameCol;

    @FXML
    private TableColumn<?, ?> officeAgeCol;

    @FXML
    private TableColumn<?, ?> officeAddressCol;

    @FXML
    private TableColumn<?, ?> officeContactCol;

    @FXML
    private TableColumn<?, ?> officeGenderCol;

    @FXML
    private TableColumn<?, ?> officePositionCol;

    @FXML
    private JFXButton officeAddEmployeeBtn;

    @FXML
    private JFXButton officeSearchEmployeeBtn;

    @FXML
    void initialize(){
        setCellValueFactory();
        setTableValues();

    }

    private void setTableValues() {
        try {
            List<OfficeTM> officeTMList = OfficeModel.getAllEmployees();
            ObservableList<OfficeTM> obList = FXCollections.observableArrayList();

            for (OfficeTM tm : officeTMList) {
                obList.add(tm);
            }

            officeTbl.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    void setCellValueFactory() {
        officeNicCol.setCellValueFactory(new PropertyValueFactory<>("nic"));
        officeNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        officeAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        officeContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        officeAgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        officeGenderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        officePositionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
    }

    @FXML
    void officeAddEmployeeBtnOnAction(ActionEvent event) {
        DashboardFormController.fullScreen.getChildren().clear();
        try {
            DashboardFormController.fullScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/office_add_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void officeSearchEmployeeBtnOnAction(ActionEvent event) {
        DashboardFormController.fullScreen.getChildren().clear();
        try {
            DashboardFormController.fullScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/office_search_form.fxml")));
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
                    JasperDesign load = JRXmlLoader.load(new File("C:\\Users\\DasunMadawa\\IdeaProjects\\project-harbourmaster\\src\\main\\resources\\js_report\\office_all_data_form.jrxml"));

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
