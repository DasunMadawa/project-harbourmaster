package lk.ijse.projectharbourmaster.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import lk.ijse.projectharbourmaster.dto.Weather;
import lk.ijse.projectharbourmaster.model.WeatherModel;
import lk.ijse.projectharbourmaster.util.Validations;

import java.io.IOException;
import java.sql.SQLException;

public class WeatherSearchDataFormController {

    @FXML
    public JFXTextField userIdTxt;

    @FXML
    public JFXTextField datetxt;

    @FXML
    private JFXTextField specialCasestxt;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private JFXTextField dateSearchtxt;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXButton removeBtn;

    @FXML
    private JFXButton backkBtn;

    @FXML
    private JFXButton mainBtn;

    @FXML
    private JFXButton saveBtn;

    private Weather weather;

    @FXML
    void initialize() {
        setUpdatable(false);
        updateBtn.setVisible(false);
        removeBtn.setVisible(false);
        setTextFieldValidations();

    }

    private void setTextFieldValidations() {
        Validations.setFocus(userIdTxt, Validations.userPattern);
        Validations.setFocus(dateSearchtxt, Validations.datePattern);
        Validations.setFocus(specialCasestxt, Validations.namePattern);
        Validations.setFocus(datetxt, Validations.datePattern);

    }

    private boolean isAllDataValidated() {
        if (specialCasestxt.getFocusColor().equals(Paint.valueOf("red")) ||
                datetxt.getFocusColor().equals(Paint.valueOf("red")) ||
                dateSearchtxt.getFocusColor().equals(Paint.valueOf("red")) ||
                userIdTxt.getFocusColor().equals(Paint.valueOf("red"))
        ) {

            return false;
        }
        return true;

    }

    @FXML
    void backkBtnOnAction(ActionEvent event) {
        DashboardFormController.fullScreen.getChildren().clear();
        DashboardFormController.menuScreen.getChildren().clear();
        try {
            DashboardFormController.fullScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml")));
            DashboardFormController.menuScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_weather_menu_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void datetxtOnAction(ActionEvent event) {

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
    void removeBtnOnAction(ActionEvent event) {
        if (weather == null) {
            new Alert(Alert.AlertType.WARNING,
                    "Search First",
                    ButtonType.OK
            ).show();
            return;
        }

        try {
            boolean isRemoved = WeatherModel.removeWeather(weather);
            if (isRemoved) {
                weather = null;
                new Alert(Alert.AlertType.INFORMATION,
                        "Data deleted",
                        ButtonType.OK
                ).show();
                userIdTxt.setText("");
                datetxt.setText("");
                dateSearchtxt.setText("");
                specialCasestxt.setText("");

                updateBtn.setVisible(false);
                removeBtn.setVisible(false);

            } else {
                new Alert(Alert.AlertType.INFORMATION,
                        "Data not deleted",
                        ButtonType.OK
                ).show();

            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Database Crash",
                    ButtonType.OK
            ).show();
            e.printStackTrace();
        }
    }

    private String dateFormateChanger() {
        String date = dateSearchtxt.getText();

        String[] dateAr = date.split("-");
        return dateAr[2]+"-" + dateAr[1] + "-" + dateAr[0] ;

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

        String dateOld = weather.getDate();

        weather.setSpecialCauses(specialCasestxt.getText() + "");
        weather.setDate(datetxt.getText() + "");

        try {
            boolean isUpdated = WeatherModel.updateWeather(weather, dateOld, weather.getTime());

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Data Updated",
                        ButtonType.OK
                ).show();

                userIdTxt.setText("");
                datetxt.setText("");
                dateSearchtxt.setText("");
                specialCasestxt.setText("");

                setUpdatable(false);

                updateBtn.setVisible(false);
                removeBtn.setVisible(false);

            } else {
                new Alert(Alert.AlertType.INFORMATION,
                        "Data Not Updated",
                        ButtonType.OK
                ).show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Database crash",
                    ButtonType.OK
            ).show();
            e.printStackTrace();
        }

    }

    @FXML
    void searchBtnOnAction(ActionEvent event) {
        if (!isAllDataValidated()) {
            new Alert(Alert.AlertType.WARNING,
                    "Fill All Data Correctly",
                    ButtonType.OK
            ).show();
            return;

        }

        try {
            weather = WeatherModel.searchWeather(userIdTxt.getText(), dateFormateChanger());

            if (weather.getSpecialCauses() == null) {
                weather.setSpecialCauses("Not Mentioned");
            }

            specialCasestxt.setText(weather.getSpecialCauses() + "");
            datetxt.setText(weather.getDate());

            updateBtn.setVisible(true);
            removeBtn.setVisible(true);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,
                    "No weather report found on that day on that user",
                    ButtonType.OK
            ).show();
            e.printStackTrace();
        }

    }

    @FXML
    void specialCasestxtOnAction(ActionEvent event) {

    }

    @FXML
    void updateBtnOnAction(ActionEvent event) {
        setUpdatable(true);

    }

    public void setUpdatable(boolean ok) {
        if (ok) {
            backkBtn.setVisible(false);
            mainBtn.setVisible(false);
            updateBtn.setVisible(false);
            removeBtn.setVisible(false);
            searchBtn.setVisible(false);

            dateSearchtxt.setEditable(false);
            userIdTxt.setEditable(false);

            specialCasestxt.setEditable(true);
            datetxt.setEditable(true);

            saveBtn.setVisible(true);
        } else {
            backkBtn.setVisible(true);
            mainBtn.setVisible(true);
            updateBtn.setVisible(true);
            removeBtn.setVisible(true);
            searchBtn.setVisible(true);

            dateSearchtxt.setEditable(true);
            userIdTxt.setEditable(true);

            specialCasestxt.setEditable(false);
            datetxt.setEditable(false);

            saveBtn.setVisible(false);

        }
    }

    @FXML
    public void userIdTxtOnAction(ActionEvent actionEvent) {

        searchBtnOnAction(new ActionEvent());
    }

    @FXML
    public void dateSearchtxtAction(ActionEvent actionEvent) {
        searchBtnOnAction(new ActionEvent());
    }
}
