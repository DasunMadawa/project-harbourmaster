package lk.ijse.projectharbourmaster.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import lk.ijse.projectharbourmaster.model.CastModel;

import java.sql.SQLException;

public class DashboardCastMenuFormController {

    @FXML
    private Pane castMenuPane;

    @FXML
    private TextField castMessagetxt;

    @FXML
    private JFXButton castCastBtn;

    @FXML
    private ComboBox<String> castCastingPeopleComboBox;

    private String selectedType;

    @FXML
    void initialize(){
        setComboBoxValue();
    }

    private void setComboBoxValue() {
        castCastingPeopleComboBox.getItems().addAll(
                "Boat Owners" ,
                "Crew Members"
        );

    }

    @FXML
    void castCastBtnOnAction(ActionEvent event) {
        if (selectedType == null){
            new Alert(Alert.AlertType.ERROR ,
                    "Select Type" ,
                    ButtonType.OK
            ).show();
            return;
        }
        String temp = castMessagetxt.getText();

        if ( temp.isEmpty() || temp.isBlank() ){
            new Alert(Alert.AlertType.ERROR ,
                    "Enter Message" ,
                    ButtonType.OK
            ).show();
            return;
        }

        new Thread(){
            @Override
            public void run(){
                boolean isCast = false;
                try {
                    isCast = CastModel.cast(selectedType , temp);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (isCast){
                    System.out.println("All are Informed");

                }else {
                    System.out.println("All are not Informed");

                }
                this.stop();
            }
        }.start();

        castMessagetxt.clear();
    }

    @FXML
    void castCastingPeopleComboBoxOnAction(ActionEvent event) {
        selectedType = castCastingPeopleComboBox.getValue();
    }

    @FXML
    void castMessagetxtOnAction(ActionEvent event) {
        castCastBtnOnAction(event);
    }

}
