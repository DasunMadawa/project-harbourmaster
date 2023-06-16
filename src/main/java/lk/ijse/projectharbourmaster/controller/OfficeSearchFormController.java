package lk.ijse.projectharbourmaster.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.projectharbourmaster.bo.BOFactory;
import lk.ijse.projectharbourmaster.bo.custom.OfficeBO;
import lk.ijse.projectharbourmaster.dto.EmployeeDTO;
//import lk.ijse.projectharbourmaster.model.OfficeModel;
import lk.ijse.projectharbourmaster.util.Validations;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class OfficeSearchFormController {

    @FXML
    public JFXButton saveBtn;

    @FXML
    public JFXButton deleteBtn;

    @FXML
    private ImageView employeeImageView;

    @FXML
    private JFXTextField nameTxt;

    @FXML
    private JFXTextField nictxt;

    @FXML
    private JFXTextField addresstxt;

    @FXML
    private JFXTextField dobtxt;

    @FXML
    private JFXTextField gendertxt;

    @FXML
    private JFXTextField contactTxt;

    @FXML
    private JFXTextField emailtxt;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton mainBtn;

    @FXML
    private JFXTextField salaryTxt;

    @FXML
    private JFXTextField positionTxt;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private JFXTextField nicSearchTxt;

    @FXML
    private JFXButton searchBtn;

    private Image image;
    private EmployeeDTO employeeDTO;
    private WritableImage tempImg;


    OfficeBO officeBO = (OfficeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.OFFICE);

    @FXML
    void initialize(){
        image = employeeImageView.getImage();
        setUpdatable(false);
        setBtns(false);
        setTextFieldValidations();

    }

    private void setTextFieldValidations() {
        Validations.setFocus(nameTxt, Validations.namePattern);
        Validations.setFocus(nictxt, Validations.idPattern);
        Validations.setFocus(addresstxt, Validations.namePattern);
        Validations.setFocus(dobtxt, Validations.datePattern);
        Validations.setFocus(contactTxt, Validations.mobilePattern);
        Validations.setFocus(emailtxt, Validations.emailPattern);
        Validations.setFocus(salaryTxt, Validations.doublePattern82);
        Validations.setFocus(positionTxt, Validations.namePattern);

    }

    private boolean isAllDataValidated() {
        if (nameTxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red")) ||
                nictxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red")) ||
                addresstxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red")) ||
                dobtxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red")) ||
                contactTxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red")) ||
                emailtxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red")) ||
                salaryTxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red")) ||
                positionTxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red"))
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

            nicSearchTxt.setEditable(false);

            nameTxt.setEditable(true);
            nictxt.setEditable(true);
            addresstxt.setEditable(true);
            dobtxt.setEditable(true);
            gendertxt.setEditable(true);
            contactTxt.setEditable(true);
            salaryTxt.setEditable(true);
            positionTxt.setEditable(true);
            emailtxt.setEditable(true);

        }else {
            updateBtn.setVisible(true);
            deleteBtn.setVisible(true);
            searchBtn.setVisible(true);

            saveBtn.setVisible(false);

            nicSearchTxt.setEditable(true);

            nameTxt.setEditable(false);
            nictxt.setEditable(false);
            addresstxt.setEditable(false);
            dobtxt.setEditable(false);
            gendertxt.setEditable(false);
            contactTxt.setEditable(false);
            salaryTxt.setEditable(false);
            positionTxt.setEditable(false);
            emailtxt.setEditable(false);

        }

    }

    @FXML
    void addresstxtOnAction(ActionEvent event) {

    }

    @FXML
    void backBtnOnAction(ActionEvent event) {
        DashboardFormController.fullScreen.getChildren().clear();
        DashboardFormController.menuScreen.getChildren().clear();
        try {
            DashboardFormController.fullScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml")));
            DashboardFormController.menuScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_office_menu_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void contactTxtOnAction(ActionEvent event) {

    }

    @FXML
    void dobtxtOnAction(ActionEvent event) {

    }

    @FXML
    void emailtxtOnAction(ActionEvent event) {

    }

    @FXML
    void employeeImageViewOnMouseClicked(MouseEvent event) {
        if (saveBtn.isVisible()){
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(new Stage());

            if (selectedFile != null) {
                try {
                    FileInputStream in = new FileInputStream(selectedFile);
                    BufferedImage bufferedImage = ImageIO.read(in);
                    WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
                    employeeImageView.setImage(image);

                    tempImg = image;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }
    }

    @FXML
    void gendertxtOnAction(ActionEvent event) {

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
    void nameTxtOnAction(ActionEvent event) {

    }

    @FXML
    void nicSearchTxt1OnAction(ActionEvent event) {
        searchBtnOnAction(event);
    }

    @FXML
    void nictxtOnAction(ActionEvent event) {

    }

    @FXML
    void positionTxtOnAction(ActionEvent event) {

    }

    @FXML
    void salaryTxtOnAction(ActionEvent event) {

    }

    @FXML
    void searchBtnOnAction(ActionEvent event) {
        try {
            employeeDTO = officeBO.searchEmployee(nicSearchTxt.getText());

            if (employeeDTO != null) {
                if (employeeDTO.getPhoto() != null) {
                    employeeImageView.setImage(employeeDTO.getPhoto());
                    tempImg = employeeDTO.getPhoto();
                }
                nictxt.setText(employeeDTO.getNic());
                nameTxt.setText(employeeDTO.getName());
                dobtxt.setText(employeeDTO.getDob());
                addresstxt.setText(employeeDTO.getAddress());
                gendertxt.setText(employeeDTO.getGender());
                salaryTxt.setText(employeeDTO.getSalary() + "");
                positionTxt.setText(employeeDTO.getPosition());
                emailtxt.setText(employeeDTO.getEmail());
                contactTxt.setText(employeeDTO.getContact());

                setBtns(true);
            }else {
                new Alert(Alert.AlertType.INFORMATION,
                        "No employee found",
                        ButtonType.OK
                ).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e){
            new Alert(Alert.AlertType.INFORMATION,
                    "photo not found",
                    ButtonType.OK
            ).show();
            e.printStackTrace();
        }
    }

    @FXML
    void updateBtnOnAction(ActionEvent event) {
        setUpdatable(true);
    }

    private String dateFormateChanger() {
        String date = dobtxt.getText();

        String[] dateAr = date.split("-");

        if (dateAr[0].length() == 4) {
            return date;
        }

        return dateAr[2]+"-" + dateAr[1] + "-" + dateAr[0] ;

    }

    @FXML
    public void saveBtnOnAction(ActionEvent actionEvent) {
        if (!isAllDataValidated()){
            new Alert(Alert.AlertType.WARNING,
                    "Fill All Data Correctly",
                    ButtonType.OK
            ).show();
            return;

        }

        String nicOld = employeeDTO.getNic();

        String nic = nictxt.getText();

        WritableImage photo = null;
        if (tempImg != null) {
            photo = (WritableImage) employeeImageView.getImage();
        }

        String name = nameTxt.getText();
        String dob = dateFormateChanger();
        String address = addresstxt.getText();
        String gender = gendertxt.getText();
        double salary = Double.parseDouble(salaryTxt.getText());
        String position = positionTxt.getText();
        String email = emailtxt.getText();
        String contact = contactTxt.getText();

        try {
            boolean isUpdated = officeBO.updateEmployee(new EmployeeDTO(nic , photo , name , dob, address, gender, salary , position , email, contact) , nicOld );

            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,
                        "Data Updated",
                        ButtonType.OK
                ).show();

                nicSearchTxt.clear();

                employeeImageView.setImage(image);
                nameTxt.clear();
                nictxt.clear();
                addresstxt.clear();
                dobtxt.clear();
                gendertxt.clear();
                contactTxt.clear();
                salaryTxt.clear();
                positionTxt.clear();
                emailtxt.clear();
                tempImg = null;

                setUpdatable(false);
                setBtns(false);

            }else {
                new Alert(Alert.AlertType.INFORMATION,
                        "Data Not Updated",
                        ButtonType.OK
                ).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteBtnOnAction(ActionEvent actionEvent) {
        try {
            boolean isDroped = officeBO.deleteEmployee(employeeDTO.getNic());

            if (isDroped){
                new Alert(Alert.AlertType.INFORMATION,
                        "Data Deleted",
                        ButtonType.OK
                ).show();

                employeeImageView.setImage(image);
                nameTxt.clear();
                nictxt.clear();
                addresstxt.clear();
                dobtxt.clear();
                gendertxt.clear();
                salaryTxt.clear();
                positionTxt.clear();
                emailtxt.clear();
                contactTxt.clear();

                nicSearchTxt.setText("");

                setBtns(false);

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

}
