package lk.ijse.projectharbourmaster.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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

public class OfficeAddFormController {

    @FXML
    public ComboBox<String> genderComboBox;

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
    private JFXTextField contactTxt;

    @FXML
    private JFXTextField emailtxt;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton mainBtn;

    @FXML
    private JFXButton registerBtn;

    @FXML
    private JFXTextField salaryTxt;

    @FXML
    private JFXTextField positionTxt;

    private Image defaultImg;


    OfficeBO officeBO = (OfficeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.OFFICE);

    @FXML
    void initialize(){
        defaultImg = employeeImageView.getImage();

        genderComboBox.getItems().addAll(
                "MALE" ,
                "FEMALE"
        );

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
        }else if (nameTxt.getText().equals("") ||
                nictxt.getText().equals("") ||
                addresstxt.getText().equals("") ||
                dobtxt.getText().equals("") ||
                contactTxt.getText().equals("") ||
                emailtxt.getText().equals("") ||
                salaryTxt.getText().equals("") ||
                positionTxt.getText().equals("") ||
                genderComboBox.getValue() == null
        ){
            return false;
        }
        return true;

    }

    @FXML
    void addresstxtOnAction(ActionEvent event) {
        registerBtnOnAction(event);

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
        registerBtnOnAction(event);
    }

    @FXML
    void dobtxtOnAction(ActionEvent event) {
        registerBtnOnAction(event);
    }

    @FXML
    void emailtxtOnAction(ActionEvent event) {
        registerBtnOnAction(event);
    }

    private String dateFormateChanger() {
        String date = dobtxt.getText();

        String[] dateAr = date.split("-");

        return dateAr[2]+"-" + dateAr[1] + "-" + dateAr[0] ;

    }

    @FXML
    void employeeImageViewOnMouseClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            try {
                FileInputStream in = new FileInputStream(selectedFile);
                BufferedImage bufferedImage = ImageIO.read(in);
                WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
                employeeImageView.setImage(image);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    void nameTxtOnAction(ActionEvent event) {
        registerBtnOnAction(event);
    }

    @FXML
    void nictxtOnAction(ActionEvent event) {
        registerBtnOnAction(event);
    }

    @FXML
    void positionTxtOnAction(ActionEvent event) {
        registerBtnOnAction(event);
    }

    @FXML
    void registerBtnOnAction(ActionEvent event) {
        if (!isAllDataValidated()){
            new Alert(Alert.AlertType.WARNING,
                    "Fill All Data Correctly",
                    ButtonType.OK
            ).show();
            return;

        }

        String nic = nictxt.getText();

        WritableImage photo;
        if(!employeeImageView.getImage().equals(defaultImg)){
            photo = (WritableImage) employeeImageView.getImage();

        }else {
            photo = null;
        }

        String name = nameTxt.getText();
        String dob = dateFormateChanger();
        String address = addresstxt.getText();
        String gender = genderComboBox.getValue();
        double salary = Double.parseDouble(salaryTxt.getText());
        String position = positionTxt.getText();
        String email = emailtxt.getText();
        String contact = contactTxt.getText();

        try {
            boolean isInserted = officeBO.inseartData(new EmployeeDTO( nic , photo , name , dob , address , gender , salary , position , email , contact ));
            if (isInserted){
                new Alert(Alert.AlertType.INFORMATION,
                        "Data Inserted",
                        ButtonType.OK
                ).show();

                employeeImageView.setImage(defaultImg);

                nictxt.clear();
                nameTxt.clear();
                dobtxt.clear();
                addresstxt.clear();
                genderComboBox.setValue("Select gender");
                salaryTxt.clear();
                positionTxt.clear();
                emailtxt.clear();
                contactTxt.clear();

            }else {
                new Alert(Alert.AlertType.INFORMATION,
                        "Data Not Inserted",
                        ButtonType.OK
                ).show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Database Crash Chech Duplicate Data",
                    ButtonType.OK
            ).show();
            e.printStackTrace();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Enter Valid Data",
                    ButtonType.OK
            ).show();
            e.printStackTrace();
        }
    }

    @FXML
    void salaryTxtOnAction(ActionEvent event) {
        registerBtnOnAction(event);
    }

    @FXML
    public void genderComboBoxOnAction(ActionEvent actionEvent) {

    }

}
