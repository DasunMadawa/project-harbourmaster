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
import lk.ijse.projectharbourmaster.bo.custom.CrewBO;
import lk.ijse.projectharbourmaster.db.DBConnection;
import lk.ijse.projectharbourmaster.dto.CrewDTO;
//import lk.ijse.projectharbourmaster.model.CrewModel;
import lk.ijse.projectharbourmaster.util.Validations;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;

public class CrewSearchFormController {

    @FXML
    public JFXButton updateBtn;

    @FXML
    public JFXButton printReportBtn;

    @FXML
    private ImageView crewImageView;

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
    private JFXButton saveBtn;

    @FXML
    private JFXTextField nicSearchTxt;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXButton deleteCrewBtn;

    private CrewDTO crewDTO;
    private Image image;
    private WritableImage tempImg;

    CrewBO crewBO = (CrewBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CREW);

    @FXML
    void initialize() {
        image = crewImageView.getImage();
        setUpdatable(false);
        setBtns(false);
        setTextFieldValidations();

    }

    private void setTextFieldValidations() {
        Validations.setFocus(nameTxt, Validations.namePattern);
        Validations.setFocus(gendertxt, Validations.genderTypePattern);
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
                gendertxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red")) ||
                dobtxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red")) ||
                contactTxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red")) ||
                emailtxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red"))
        ) {

            return false;
        }
        return true;

    }

    void setBtns(boolean ok) {
        if (ok) {
            updateBtn.setVisible(true);
            deleteCrewBtn.setVisible(true);

        } else {
            updateBtn.setVisible(false);
            deleteCrewBtn.setVisible(false);

        }

    }

    void setUpdatable(boolean updatable) {
        if (updatable) {
            updateBtn.setVisible(false);
            deleteCrewBtn.setVisible(false);
            searchBtn.setVisible(false);

            saveBtn.setVisible(true);

            nicSearchTxt.setEditable(false);

            nameTxt.setEditable(true);
            nictxt.setEditable(true);
            addresstxt.setEditable(true);
            dobtxt.setEditable(true);
            gendertxt.setEditable(true);
            contactTxt.setEditable(true);
            emailtxt.setEditable(true);

        } else {
            updateBtn.setVisible(true);
            deleteCrewBtn.setVisible(true);
            searchBtn.setVisible(true);

            saveBtn.setVisible(false);

            nicSearchTxt.setEditable(true);

            nameTxt.setEditable(false);
            nictxt.setEditable(false);
            addresstxt.setEditable(false);
            dobtxt.setEditable(false);
            gendertxt.setEditable(false);
            contactTxt.setEditable(false);
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
            DashboardFormController.menuScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_crew_menu_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void contactTxtOnAction(ActionEvent event) {

    }

    private String dateFormateChanger() {
        String date = dobtxt.getText();

        String[] dateAr = date.split("-");

        if (dateAr[0].length() == 4) {
            return date;
        }

        return dateAr[2] + "-" + dateAr[1] + "-" + dateAr[0];

    }

    @FXML
    void deleteCrewBtnOnAction(ActionEvent event) {
        try {
            boolean isDroped = crewBO.deleteCrew(crewDTO.getNic());

            if (isDroped) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Data Deleted",
                        ButtonType.OK
                ).show();

                crewDTO = null;

                crewImageView.setImage(image);
                nameTxt.clear();
                nictxt.clear();
                addresstxt.clear();
                dobtxt.clear();
                gendertxt.clear();
                contactTxt.clear();
                emailtxt.clear();

                nicSearchTxt.setText("");

                setBtns(false);

            } else {
                new Alert(Alert.AlertType.INFORMATION,
                        "Data Not Deleted",
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
    void dobtxtOnAction(ActionEvent event) {

    }

    @FXML
    void emailtxtOnAction(ActionEvent event) {

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
        searchBtnOnAction(new ActionEvent());
    }

    @FXML
    void nictxtOnAction(ActionEvent event) {

    }

    @FXML
    void saveBtnOnAction(ActionEvent event) {
        if (!isAllDataValidated()) {
            new Alert(Alert.AlertType.WARNING,
                    "Fill All Data Correctly",
                    ButtonType.OK
            ).show();
            return;
        }

        String nicOld = crewDTO.getNic();

        String nic = nictxt.getText();
        String name = nameTxt.getText();

        WritableImage photo = null;
        if (tempImg != null) {
            photo = (WritableImage) crewImageView.getImage();
        }

        String dob = dateFormateChanger();
        String address = addresstxt.getText();
        String gender = gendertxt.getText();
        String email = emailtxt.getText();
        String contact = contactTxt.getText();

        try {
            boolean isUpdated = crewBO.updateCrew(new CrewDTO(nic, name, photo, dob, address, gender, email, contact), nicOld);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Data Updated",
                        ButtonType.OK
                ).show();

                crewDTO = null;

                nicSearchTxt.clear();

                crewImageView.setImage(image);
                nameTxt.clear();
                nictxt.clear();
                addresstxt.clear();
                dobtxt.clear();
                gendertxt.clear();
                contactTxt.clear();
                emailtxt.clear();
                tempImg = null;

                setUpdatable(false);
                setBtns(false);

            } else {
                new Alert(Alert.AlertType.INFORMATION,
                        "Data Not Updated Check Duplicate Data",
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
    void searchBtnOnAction(ActionEvent event) {
        try {
            crewDTO = crewBO.searchCrew(nicSearchTxt.getText());

            if (crewDTO != null) {
                if (crewDTO.getPhoto() != null) {
                    crewImageView.setImage(crewDTO.getPhoto());
                    tempImg = crewDTO.getPhoto();
                }
                nameTxt.setText(crewDTO.getName());
                nictxt.setText(crewDTO.getNic());
                addresstxt.setText(crewDTO.getAddress());
                dobtxt.setText(crewDTO.getDob());
                gendertxt.setText(crewDTO.getGender());
                contactTxt.setText(crewDTO.getContact());
                emailtxt.setText(crewDTO.getEmail());

                setBtns(true);
            } else {
                new Alert(Alert.AlertType.INFORMATION,
                        "No crew found",
                        ButtonType.OK
                ).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            new Alert(Alert.AlertType.INFORMATION,
                    "photo not found",
                    ButtonType.OK
            ).show();
            e.printStackTrace();
        }
    }

    @FXML
    public void updateBtnOnAction(ActionEvent actionEvent) {
        setUpdatable(true);

    }

    @FXML
    public void crewImgViewOnMouseClicked(MouseEvent mouseEvent) {
        if (saveBtn.isVisible()) {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(new Stage());

            if (selectedFile != null) {
                try {
                    FileInputStream in = new FileInputStream(selectedFile);
                    BufferedImage bufferedImage = ImageIO.read(in);
                    WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
                    crewImageView.setImage(image);
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
    public void printReportBtnOnAction(ActionEvent actionEvent) {
        if (crewDTO != null) {
            new Thread() {
                @Override
                public void run() {
                    JasperDesign load = null;
                    try {
                        load = JRXmlLoader.load(new File("C:\\Users\\DasunMadawa\\IdeaProjects\\project-harbourmaster\\src\\main\\resources\\js_report\\crew_single_data_form.jrxml"));

                        JRDesignQuery newQuery = new JRDesignQuery();
                        String sql = "SELECT nic , name , DATE_FORMAT(crew.bod , '%Y-%m-%d') as Date , address , gender , email , contact FROM crew WHERE nic = '" + crewDTO.getNic() + "'";
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
        } else {
            new Alert(Alert.AlertType.INFORMATION,
                    "Search Crew First",
                    ButtonType.OK
            ).show();
        }

    }

}
