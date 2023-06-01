package lk.ijse.projectharbourmaster.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.projectharbourmaster.dto.User;
import lk.ijse.projectharbourmaster.dto.WeatherAPI;
import lk.ijse.projectharbourmaster.dto.tm.TurnTM;
import lk.ijse.projectharbourmaster.model.DockModel;
import lk.ijse.projectharbourmaster.model.TurnCrewModel;
import lk.ijse.projectharbourmaster.model.TurnModel;
import lk.ijse.projectharbourmaster.model.WeatherModel;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DashboardMainMenuFormController {

    public static User user;

    @FXML
    private Pane mainMenuPane;

    @FXML
    private Label mainMenuWeatherDaylbl;

    @FXML
    private Label mainMenuWeatherWSpeedlbl;

    @FXML
    private Label mainMenuWeatherSCaseslbl;

    @FXML
    private Label mainMenuWeatherThreatLvllbl;

    @FXML
    private Label mainMenuTurnNoBoatsOnSealbl;

    @FXML
    private Label mainMenuTurnNoSailorsOnSealbl;

    @FXML
    private TableView<TurnTM> mainMenuTurnTbl;

    @FXML
    private TableColumn<?, ?> mainMenuTurnTIdCol;

    @FXML
    private TableColumn<?, ?> mainMenuTurnOutDateCol;

    @FXML
    private TableColumn<?, ?> mainMenuTurnCrewcol;

    @FXML
    private TableColumn<?, ?> mainMenuTurnCaptainCol;

    @FXML
    private Label D111;

    @FXML
    private Label D112;

    @FXML
    private Label D121;

    @FXML
    private Label D122;

    @FXML
    private Label D131;

    @FXML
    private Label D132;

    @FXML
    private Label D311;

    @FXML
    private Label D312;

    @FXML
    private Label D321;

    @FXML
    private Label D322;

    @FXML
    private Label D331;

    @FXML
    private Label D332;

    @FXML
    private Label D211;

    @FXML
    private Label D212;

    @FXML
    private Label D221;

    @FXML
    private Label D222;

    @FXML
    private Label D231;

    @FXML
    private Label D232;

    public static int round;

    @FXML
    public void initialize(){
        if (round == 0) {
            new Thread() {
                @Override
                public void run() {
                    DashboardWeatherMenuFormController.weatherAPI = informationGatherAPI();
                    this.stop();
                }

            }.start();

            round++;

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        setDailyInformation();
        setAvailableDocks();
        setTurnReport();
        setCellValueFactory();

    }

    void setCellValueFactory() {
        mainMenuTurnTIdCol.setCellValueFactory(new PropertyValueFactory<>("turnId"));
        mainMenuTurnCaptainCol.setCellValueFactory(new PropertyValueFactory<>("capNic"));
        mainMenuTurnCrewcol.setCellValueFactory(new PropertyValueFactory<>("noCrew"));
        mainMenuTurnOutDateCol.setCellValueFactory(new PropertyValueFactory<>("outDate"));

    }

    private void setTurnReport() {
        try {

            List<String> allBoatsInSea = TurnModel.getAllBoatsInSea();
            List<String> allCrewInSea = TurnCrewModel.getAllCrewInSea();

            mainMenuTurnNoBoatsOnSealbl.setText(allBoatsInSea.size()+"");
            mainMenuTurnNoSailorsOnSealbl.setText(allCrewInSea.size()+"");

            List<TurnTM> warningTurns = TurnModel.getAllInCompletedTurnsInDanger();

            ObservableList<TurnTM> turnTMObservableList = FXCollections.observableArrayList();

            for (TurnTM turnTM : warningTurns) {
                turnTMObservableList.add(turnTM);
            }

            mainMenuTurnTbl.setItems(turnTMObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void setAvailableDocks() {
        try {
            D111.setText(DockModel.getAvailableCount("D111") + "");
            D112.setText(DockModel.getAvailableCount("D112") + "");
            D121.setText(DockModel.getAvailableCount("D121") + "");
            D122.setText(DockModel.getAvailableCount("D122") + "");
            D131.setText(DockModel.getAvailableCount("D131") + "");
            D132.setText(DockModel.getAvailableCount("D132") + "");
            D211.setText(DockModel.getAvailableCount("D211") + "");
            D212.setText(DockModel.getAvailableCount("D212") + "");
            D221.setText(DockModel.getAvailableCount("D221") + "");
            D222.setText(DockModel.getAvailableCount("D222") + "");
            D231.setText(DockModel.getAvailableCount("D231") + "");
            D232.setText(DockModel.getAvailableCount("D232") + "");
            D311.setText(DockModel.getAvailableCount("D311") + "");
            D312.setText(DockModel.getAvailableCount("D312") + "");
            D321.setText(DockModel.getAvailableCount("D321") + "");
            D322.setText(DockModel.getAvailableCount("D322") + "");
            D331.setText(DockModel.getAvailableCount("D331") + "");
            D332.setText(DockModel.getAvailableCount("D332") + "");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    void setDailyInformation(){
        if (DashboardWeatherMenuFormController.weatherAPI == null){
            new Alert(Alert.AlertType.INFORMATION,
                    "Connect To the Internet",
                    ButtonType.OK
            ).show();
            return;
        }

        if (DashboardWeatherMenuFormController.weatherAPI.getWsTodat_Kmh() > 37) {
            mainMenuWeatherThreatLvllbl.setText("low");
        } else if (DashboardWeatherMenuFormController.weatherAPI.getWsTodat_Kmh() > 62) {
            mainMenuWeatherThreatLvllbl.setText("medium");
        } else if (DashboardWeatherMenuFormController.weatherAPI.getWsTodat_Kmh() > 88) {
            mainMenuWeatherThreatLvllbl.setText("high");
        } else if (DashboardWeatherMenuFormController.weatherAPI.getWsTodat_Kmh() > 118) {
            mainMenuWeatherThreatLvllbl.setText("critical");
        } else {
            mainMenuWeatherThreatLvllbl.setText("stable");
        }

        mainMenuWeatherWSpeedlbl.setText(DashboardWeatherMenuFormController.weatherAPI.getWsTodat_Kmh()+" Kmh");
        mainMenuWeatherDaylbl.setText(LocalDate.now()+"");

        String specialCauses = null;
        try {
            specialCauses = WeatherModel.searchWeatherSpecialCauses(LocalDate.now()+"");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (specialCauses != null){
            mainMenuWeatherSCaseslbl.setText(specialCauses);
            mainMenuWeatherThreatLvllbl.setText("critical");
        }else {
            mainMenuWeatherSCaseslbl.setText("No");
        }

    }

    public WeatherAPI informationGatherAPI(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://weatherapi-com.p.rapidapi.com/forecast.json?q=Beruwala&days=14"))
                .header("X-RapidAPI-Key", "b01129e8fcmsh04f353984676b9fp1b2e56jsn11e9ba0e2ca6")
                .header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;

        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String s = response.body();

        String[] splitTemp = s.split("[\"][a][v][g][t][e][m][p][_][c][\"][:]");
        double tempToday = Double.valueOf(splitTemp[1].substring(0,4));
        double tempTommorrow = Double.valueOf(splitTemp[2].substring(0,4));
        double tempDayAfterTommorrow = Double.valueOf(splitTemp[3].substring(0,4));

        String[] splitWindSpeed = s.split("[\"][m][a][x][w][i][n][d][_][k][p][h][\"][:]");
        double wsToday = windSpeedSpliter(splitWindSpeed[1]);
        double wsTommorrow = windSpeedSpliter(splitWindSpeed[1]);
        double wsDayAfterTommorrow = windSpeedSpliter(splitWindSpeed[1]);

        return new WeatherAPI(tempTommorrow , wsTommorrow , tempToday , wsToday);

    }

    public double windSpeedSpliter(String sentance){
        int index = 0;
        for (int i = 0; i <sentance.length(); i++){
            if (sentance.charAt(i) == ','){
                index = i;
                break;
            }
        }

        return Double.valueOf(sentance.substring(0,index));

    }

}
