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
import lk.ijse.projectharbourmaster.bo.BOFactory;
import lk.ijse.projectharbourmaster.bo.custom.TurnBO;
import lk.ijse.projectharbourmaster.dto.TurnDTO;
import lk.ijse.projectharbourmaster.dto.tm.TurnTM;
//import lk.ijse.projectharbourmaster.model.TurnModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DashboardTurnMenuFormController {

    @FXML
    private Pane turnMenuPane;

    @FXML
    private TableView<TurnTM> turnCompleteTurnTbl;

    @FXML
    private TableColumn<?, ?> turnTurnIdCol;

    @FXML
    private TableColumn<?, ?> turnBoatIdCol;

    @FXML
    private TableColumn<?, ?> turnNoCrewCol;

    @FXML
    private TableColumn<?, ?> turnCaptainCol;

    @FXML
    private TableColumn<?, ?> turnOutDateCol;

    @FXML
    private TableColumn<?, ?> turnOutTimeCol;

    @FXML
    private TableColumn<?, ?> turnInDateCol;

    @FXML
    private TableColumn<?, ?> turnInTimeCol;

    @FXML
    private JFXButton turnSearchTurnBtn;

    @FXML
    private TableView<TurnTM> turnIncompleteTurnTable;

    @FXML
    private TableColumn<?, ?> turnTurnIdCol1;

    @FXML
    private TableColumn<?, ?> turnBoatIdCol1;

    @FXML
    private TableColumn<?, ?> turnNoCrewCol1;

    @FXML
    private TableColumn<?, ?> turnCaptainCol1;

    @FXML
    private TableColumn<?, ?> turnOutDateCol1;

    @FXML
    private TableColumn<?, ?> turnOutTimeCol1;

    @FXML
    private TableColumn<?, ?> turnInDateCol1;

    @FXML
    private TableColumn<?, ?> turnInTimeCol1;

    @FXML
    private TableColumn<?, ?> turnThreatCol11;

    @FXML
    private JFXButton turnStartTurnBtn;

    List <TurnTM> completedTurns;
    List <TurnTM> inCompletedTurns;


    TurnBO turnBO = (TurnBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.TURN);

    @FXML
    void initialize(){
        setCellValueFactory();
        setTables();

    }

    void setCellValueFactory() {
        turnTurnIdCol.setCellValueFactory(new PropertyValueFactory<>("turnId"));
        turnBoatIdCol.setCellValueFactory(new PropertyValueFactory<>("boatId"));
        turnCaptainCol.setCellValueFactory(new PropertyValueFactory<>("capNic"));
        turnNoCrewCol.setCellValueFactory(new PropertyValueFactory<>("noCrew"));
        turnOutDateCol.setCellValueFactory(new PropertyValueFactory<>("outDate"));
        turnOutTimeCol.setCellValueFactory(new PropertyValueFactory<>("outTime"));
        turnInDateCol.setCellValueFactory(new PropertyValueFactory<>("inDate"));
        turnInTimeCol.setCellValueFactory(new PropertyValueFactory<>("inTime"));

        turnTurnIdCol1.setCellValueFactory(new PropertyValueFactory<>("turnId"));
        turnBoatIdCol1.setCellValueFactory(new PropertyValueFactory<>("boatId"));
        turnCaptainCol1.setCellValueFactory(new PropertyValueFactory<>("capNic"));
        turnNoCrewCol1.setCellValueFactory(new PropertyValueFactory<>("noCrew"));
        turnOutDateCol1.setCellValueFactory(new PropertyValueFactory<>("outDate"));
        turnOutTimeCol1.setCellValueFactory(new PropertyValueFactory<>("outTime"));
        turnInDateCol1.setCellValueFactory(new PropertyValueFactory<>("inDate"));
        turnInTimeCol1.setCellValueFactory(new PropertyValueFactory<>("inTime"));


    }

    private void setTables() {
        try {
            for (TurnDTO turnDTO : turnBO.getAllCompletedTurns() ) {
                completedTurns.add(
                        new TurnTM(
                                turnDTO.getTurnId(),
                                turnDTO.getBoatId(),
                                turnDTO.getCapNIC(),
                                turnDTO.getCrewCount(),
                                turnDTO.getOutDate(),
                                turnDTO.getOutTime(),
                                turnDTO.getInDate(),
                                turnDTO.getInTime()
                        )
                );
            }

            for (TurnDTO turnDTO : turnBO.getAllInCompletedTurns() ) {
                inCompletedTurns.add(
                        new TurnTM(
                                turnDTO.getTurnId(),
                                turnDTO.getBoatId(),
                                turnDTO.getCapNIC(),
                                turnDTO.getCrewCount(),
                                turnDTO.getOutDate(),
                                turnDTO.getOutTime(),
                                turnDTO.getInDate(),
                                turnDTO.getInTime()
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<TurnTM> completedTurnsObList = FXCollections.observableArrayList();
        ObservableList<TurnTM> inCopletedTurnsObList = FXCollections.observableArrayList();

        for (TurnTM turnTM : completedTurns) {
            completedTurnsObList.add(turnTM);
        }

        for (TurnTM turnTM : inCompletedTurns) {
            inCopletedTurnsObList.add(turnTM);
        }

        turnCompleteTurnTbl.setItems(completedTurnsObList);
        turnIncompleteTurnTable.setItems(inCopletedTurnsObList);

    }

    @FXML
    void turnSearchTurnBtnOnAction(ActionEvent event) {
        DashboardFormController.fullScreen.getChildren().clear();
        try {
            DashboardFormController.fullScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/turn_search_turn_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void turnStartTurnBtn(ActionEvent event) {
        DashboardFormController.fullScreen.getChildren().clear();
        try {
            DashboardFormController.fullScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/turn_start_turn_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
