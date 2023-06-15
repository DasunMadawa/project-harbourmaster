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
import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.BoatDTO;
//import lk.ijse.projectharbourmaster.model.BoatModel;
import lk.ijse.projectharbourmaster.util.Validations;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class BoatSearchFormController {

    @FXML
    public JFXButton deleteBtn;

    @FXML
    public JFXButton printReportBtn;

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
    private JFXTextField boatOwnerTxt;

    @FXML
    private JFXTextField emailtxt;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXTextField boatIdSearchTxt;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton mainBtn;

    private BoatDTO boatDTO;

    BoatBO boatBO = (BoatBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BOAT);

    @FXML
    void initialize(){
        setUpdatable(false);
        setBtns(false);
        setTextFieldValidations();

    }

    private void setTextFieldValidations() {
        Validations.setFocus(boatIdtxt, Validations.boatPattern);
        Validations.setFocus(boatIdSearchTxt, Validations.boatPattern);
        Validations.setFocus(boatNametxt, Validations.namePattern);
        Validations.setFocus(boatTypetxt, Validations.boatTypePattern);
        Validations.setFocus(noCrewtxt, Validations.intPattern2);
        Validations.setFocus(fuelCaptxt, Validations.doublePattern22);
        Validations.setFocus(waterCaptxt, Validations.doublePattern22);
        Validations.setFocus(boatOwnerTxt, Validations.namePattern);
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
                boatOwnerTxt.getFocusColor().equals(Paint.valueOf("red")) ||
                emailtxt.getFocusColor().equals(Paint.valueOf("red")) ||
                maxWeighttxt.getFocusColor().equals(Paint.valueOf("red"))
        ) {
            return false;
        }
        return true;

    }

    void setBtns(boolean ok){
        if (ok){
            updateBtn.setVisible(true);
            deleteBtn.setVisible(true);

        }else {
            updateBtn.setVisible(false);
            deleteBtn.setVisible(false);

        }

    }

    void setUpdatable(boolean updatable){
        if (updatable){
            updateBtn.setVisible(false);
            deleteBtn.setVisible(false);
            searchBtn.setVisible(false);

            saveBtn.setVisible(true);

            boatIdSearchTxt.setEditable(false);

            boatIdtxt.setEditable(true);
            boatOwnerTxt.setEditable(true);
            boatNametxt.setEditable(true);
            boatTypetxt.setEditable(true);
            noCrewtxt.setEditable(true);
            fuelCaptxt.setEditable(true);
            waterCaptxt.setEditable(true);
            maxWeighttxt.setEditable(true);
            emailtxt.setEditable(true);

        }else {
            updateBtn.setVisible(true);
            deleteBtn.setVisible(true);
            searchBtn.setVisible(true);

            saveBtn.setVisible(false);

            boatIdSearchTxt.setEditable(true);

            boatIdtxt.setEditable(false);
            boatOwnerTxt.setEditable(false);
            boatNametxt.setEditable(false);
            boatTypetxt.setEditable(false);
            noCrewtxt.setEditable(false);
            fuelCaptxt.setEditable(false);
            waterCaptxt.setEditable(false);
            maxWeighttxt.setEditable(false);
            emailtxt.setEditable(false);

        }

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
    void boatIdSearchTxtOnAction(ActionEvent event) {
        searchBtnOnAction(new ActionEvent());
    }

    @FXML
    void boatIdtxtOnAction(ActionEvent event) {

    }

    @FXML
    void boatNametxtOnAction(ActionEvent event) {

    }

    @FXML
    void boatOwnerTxtOnAction(ActionEvent event) {

    }

    @FXML
    void boatTypetxtOnAction(ActionEvent event) {

    }

    @FXML
    void emailtxtOnAction(ActionEvent event) {

    }

    @FXML
    void fuelCaptxtOnAction(ActionEvent event) {

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

    }

    @FXML
    void noCrewtxtOnAction(ActionEvent event) {

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


        String boatId = boatIdtxt.getText();
        String boatOwner = boatOwnerTxt.getText();
        String boatName = boatNametxt.getText();
        String boatType = boatTypetxt.getText();
        int noCrew = Integer.parseInt(noCrewtxt.getText());
        double fuelCap = Double.parseDouble(fuelCaptxt.getText());
        double waterCap = Double.parseDouble(waterCaptxt.getText());
        double maxWeight = Double.parseDouble(maxWeighttxt.getText());
        String email = emailtxt.getText();



        try {
            boolean isUpdated = boatBO.updateBoat(new BoatDTO(boatId , boatOwner ,boatName , boatType , noCrew , fuelCap , waterCap , maxWeight , email) , boatIdSearchTxt.getText() );

            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,
                        "Data Updated",
                        ButtonType.OK
                ).show();

                boatDTO = null;

                boatIdtxt.setText("");
                boatOwnerTxt.setText("");
                boatNametxt.setText("");
                boatTypetxt.setText("");
                noCrewtxt.setText("");
                fuelCaptxt.setText("");
                waterCaptxt.setText("");
                maxWeighttxt.setText("");
                emailtxt.setText("");
                boatIdSearchTxt.setText("");

                setUpdatable(false);
                setBtns(false);

            }else {
                new Alert(Alert.AlertType.INFORMATION,
                        "Data Not Updated",
                        ButtonType.OK
                ).show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,
                    "Database Crash Check Duplicate BoatId",
                    ButtonType.OK
            ).show();
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    @FXML
    void searchBtnOnAction(ActionEvent event) {
        try {
            boatDTO = boatBO.searchBoat(boatIdSearchTxt.getText());

            if (boatDTO != null) {

                boatIdtxt.setText(boatDTO.getBoatId());
                boatOwnerTxt.setText(boatDTO.getBoatOwner());
                boatNametxt.setText(boatDTO.getBoatName());
                boatTypetxt.setText(boatDTO.getBoatType());
                noCrewtxt.setText(boatDTO.getNoCrew()+"");
                fuelCaptxt.setText(boatDTO.getFuelCap()+"");
                waterCaptxt.setText(boatDTO.getWaterCap()+"");
                maxWeighttxt.setText(boatDTO.getMaxWeight()+"");
                emailtxt.setText(boatDTO.getEmail());

                setBtns(true);
            }else {
                new Alert(Alert.AlertType.INFORMATION,
                        "No boat found",
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
    void updateBtnOnAction(ActionEvent event) {
        setUpdatable(true);
    }

    @FXML
    void waterCaptxtOnAction(ActionEvent event) {

    }

    @FXML
    public void deleteBtnOnAction(ActionEvent actionEvent) {
        try {
            boolean isDroped = boatBO.deleteBoat(boatDTO.getBoatId());

            if (isDroped){
                new Alert(Alert.AlertType.INFORMATION,
                        "Data Deleted",
                        ButtonType.OK
                ).show();

                boatDTO = null;

                boatIdtxt.setText("");
                boatOwnerTxt.setText("");
                boatNametxt.setText("");
                boatTypetxt.setText("");
                noCrewtxt.setText("");
                fuelCaptxt.setText("");
                waterCaptxt.setText("");
                maxWeighttxt.setText("");
                emailtxt.setText("");
                boatIdSearchTxt.setText("");

                setBtns(false);
                setUpdatable(false);

            }else {
                new Alert(Alert.AlertType.INFORMATION,
                        "Data Not Deleted",
                        ButtonType.OK
                ).show();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void printReportBtnOnAction(ActionEvent actionEvent) {
        if (boatDTO != null){
            new Thread() {
                @Override
                public void run() {
                    JasperDesign load = null;
                    try {
                        load = JRXmlLoader.load(new File("C:\\Users\\DasunMadawa\\IdeaProjects\\project-harbourmaster\\src\\main\\resources\\js_report\\boat_single_data_form.jrxml"));

                        JRDesignQuery newQuery = new JRDesignQuery();
                        String sql = "SELECT * FROM boat WHERE boatId = '" + boatDTO.getBoatId() + "'";
                        newQuery.setText(sql);
                        load.setQuery(newQuery);
                        JasperReport js = JasperCompileManager.compileReport(load);
                        JasperPrint jp = JasperFillManager.fillReport(js, null, DBConnection.getInstance().getConnection());
                        JasperViewer viewer = new JasperViewer(jp, false);
                        viewer.show();

                    } catch (JRException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }else {
            new Alert(Alert.AlertType.INFORMATION,
                    "Search Boat First",
                    ButtonType.OK
            ).show();
        }
    }

}
