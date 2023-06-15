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
import lk.ijse.projectharbourmaster.bo.custom.CrewBO;
import lk.ijse.projectharbourmaster.dto.CrewDTO;
//import lk.ijse.projectharbourmaster.model.CrewModel;
import lk.ijse.projectharbourmaster.util.Validations;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class CrewAddFormController {

    @FXML
    public ComboBox<String> genderComboBox;

    @FXML
    private ImageView crewImage;

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

    private Image defaultImg;

    CrewBO crewBO = (CrewBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CREW);

    @FXML
    void initialize() {
        defaultImg = crewImage.getImage();

        genderComboBox.getItems().addAll(
                "MALE",
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

    }

    private boolean isAllDataValidated() {
        if (nameTxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red")) ||
                nictxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red")) ||
                addresstxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red")) ||
                dobtxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red")) ||
                contactTxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red")) ||
                emailtxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red"))
        ) {

            return false;
        } else if (nameTxt.getText().equals("") ||
                nictxt.getText().equals("") ||
                addresstxt.getText().equals("") ||
                dobtxt.getText().equals("") ||
                contactTxt.getText().equals("") ||
                emailtxt.getText().equals("") ||
                genderComboBox.getValue() == null
        ) {
            return false;
        }

        return true;

    }


    @FXML
    void addresstxtOnAction(ActionEvent event) {
        registerBtnOnAction(new ActionEvent());
    }

    @FXML
    void backBtnOnAction(ActionEvent event) {
        DashboardFormController.fullScreen.getChildren().clear();
        DashboardFormController.menuScreen.getChildren().clear();
        try {
            DashboardFormController.fullScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml")));
            DashboardFormController.menuScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_crew_menu_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void contactTxtOnAction(ActionEvent event) {
        registerBtnOnAction(new ActionEvent());
    }

    @FXML
    void dobtxtOnAction(ActionEvent event) {
        registerBtnOnAction(new ActionEvent());
    }

    @FXML
    void emailtxtOnAction(ActionEvent event) {
        registerBtnOnAction(new ActionEvent());
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
        registerBtnOnAction(new ActionEvent());
    }

    @FXML
    void nictxtOnAction(ActionEvent event) {
        registerBtnOnAction(new ActionEvent());
    }

    @FXML
    void registerBtnOnAction(ActionEvent event) {

        if (!isAllDataValidated()) {
            new Alert(Alert.AlertType.WARNING,
                    "Fill All Data Correctly",
                    ButtonType.OK
            ).show();
            return;
        }

        String date = dateFormateChanger();

        String nic = nictxt.getText();
        String name = nameTxt.getText();

        WritableImage photo;
        if (!crewImage.getImage().equals(defaultImg)) {
            photo = (WritableImage) crewImage.getImage();

        } else {
            photo = null;
        }


        String dob = date;

        String address = addresstxt.getText();
        String gender = genderComboBox.getValue();
        String email = emailtxt.getText();
        String contact = contactTxt.getText();

        try {
            boolean isInserted = crewBO.addCrew(new CrewDTO(nic, name, photo, dob, address, gender, email, contact));
            if (isInserted) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Data Inserted",
                        ButtonType.OK
                ).show();

                crewImage.setImage(defaultImg);

                nictxt.clear();
                nameTxt.clear();
                dobtxt.clear();
                addresstxt.clear();
                genderComboBox.setValue("Select gender");
                emailtxt.clear();
                contactTxt.clear();
            } else {
                new Alert(Alert.AlertType.INFORMATION,
                        "Data Not Inserted",
                        ButtonType.OK
                ).show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Check Duplicate Data",
                    ButtonType.OK
            ).show();
            e.printStackTrace();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,
                    "File not Found",
                    ButtonType.OK
            ).show();
            e.printStackTrace();
        }

    }

    private String dateFormateChanger() {
        String date = dobtxt.getText();

        String[] dateAr = date.split("-");

        return dateAr[2] + "-" + dateAr[1] + "-" + dateAr[0];

    }


    @FXML
    public void crewImageViewOnMouseClicked(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            try {
                FileInputStream in = new FileInputStream(selectedFile);
                BufferedImage bufferedImage = ImageIO.read(in);
                WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
                crewImage.setImage(image);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    public void genderComboBoxOnAction(ActionEvent actionEvent) {

    }
}
