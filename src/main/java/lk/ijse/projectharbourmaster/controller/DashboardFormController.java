package lk.ijse.projectharbourmaster.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DashboardFormController{

    @FXML
    public Label dateIdLbl;

    @FXML
    public Label timeIdLbl;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton mainMenuBtn;

    @FXML
    private JFXButton weatherBtn;

    @FXML
    private JFXButton turnBtn;

    @FXML
    private JFXButton dockBtn;

    @FXML
    private JFXButton crewBtn;

    @FXML
    private JFXButton boatBtn;

    @FXML
    private JFXButton fishBtn;

    @FXML
    private JFXButton stockBtn;

    @FXML
    private JFXButton reportBtn;

    @FXML
    private JFXButton castBtn;

    @FXML
    private JFXButton officeBtn;

    @FXML
    private JFXButton userBtn;

    @FXML
    private JFXButton logOutBtn;

    @FXML
    private AnchorPane menuOptionAnchorPane;

    public static AnchorPane fullScreen;

    public static AnchorPane menuScreen;

    @FXML
    public void initialize(){
        setUser();

        fullScreen = root;
        menuScreen = menuOptionAnchorPane;

        mainMenuBtnOnAction(new ActionEvent());

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeIdLbl.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                dateIdLbl.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        };
        timer.start();

    }

    private void setUser() {
        if (!LoginFormController.userId.equals("U001")){
            userBtn.setVisible(false);
            officeBtn.setVisible(false);
        }
    }


    @FXML
    void boatBtnOnAction(ActionEvent event) {
        menuOptionAnchorPane.getChildren().clear();
        try {
            menuOptionAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_boat_menu_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void castBtnOnAction(ActionEvent event) {
        menuOptionAnchorPane.getChildren().clear();
        try {
            menuOptionAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_cast_menu_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void crewBtnOnAction(ActionEvent event) {
        menuOptionAnchorPane.getChildren().clear();
        try {
            menuOptionAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_crew_menu_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void dockBtnOnAction(ActionEvent event) {
        menuOptionAnchorPane.getChildren().clear();
        try {
            menuOptionAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_dock_menu_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void fishBtnOnAction(ActionEvent event) {
        menuOptionAnchorPane.getChildren().clear();
        try {
            menuOptionAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_fish_menu_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void mainMenuBtnOnAction(ActionEvent event) {
        menuOptionAnchorPane.getChildren().clear();
        try {
            menuOptionAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_main_menu_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void officeBtnOnAction(ActionEvent event) {
        menuOptionAnchorPane.getChildren().clear();
        try {
            menuOptionAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_office_menu_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void reportBtnOnAction(ActionEvent event) {
        menuOptionAnchorPane.getChildren().clear();
        try {
            menuOptionAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_report_menu_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void stockBtnOnAction(ActionEvent event) {
        menuOptionAnchorPane.getChildren().clear();
        try {
            menuOptionAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_stock_menu_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void turnBtnOnAction(ActionEvent event) {
        menuOptionAnchorPane.getChildren().clear();
        try {
            menuOptionAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_turn_menu_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void userBtnOnAction(ActionEvent event) {
        menuOptionAnchorPane.getChildren().clear();
        try {
            menuOptionAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_users_menu_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void weatherBtnOnAction(ActionEvent event) throws IOException {
        menuOptionAnchorPane.getChildren().clear();
        try {
            menuOptionAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_weather_menu_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logOutBtnOnAction(ActionEvent event) {
        fullScreen.getChildren().clear();
        try {
            fullScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/login_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
