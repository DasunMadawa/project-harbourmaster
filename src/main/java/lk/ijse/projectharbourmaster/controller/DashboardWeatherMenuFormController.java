package lk.ijse.projectharbourmaster.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lk.ijse.projectharbourmaster.bo.BOFactory;
import lk.ijse.projectharbourmaster.bo.custom.WeatherBO;
import lk.ijse.projectharbourmaster.dto.WeatherDTO;
import lk.ijse.projectharbourmaster.dto.WeatherAPIDTO;
import lk.ijse.projectharbourmaster.dto.tm.WeatherTM;
import lk.ijse.projectharbourmaster.util.EmailUtil;
//import lk.ijse.projectharbourmaster.model.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class DashboardWeatherMenuFormController {

    @FXML
    private Pane weatherMenuPane;

    @FXML
    private JFXButton weatherInputDataBtn;

    @FXML
    private JFXButton weatherSearchSpecificDateBtn;

    @FXML
    private ImageView criticleImg;

    @FXML
    private ImageView threatImg;

    @FXML
    private ImageView stableImg;

    @FXML
    private TableView<WeatherTM> weatherTbl;

    @FXML
    private TableColumn<?, ?> weatherDateCol;

    @FXML
    private TableColumn<?, ?> weatherWindSpeedCol;

    @FXML
    private TableColumn<?, ?> weatherSpecialCasesCol;

    @FXML
    private TableColumn<?, ?> weatherThreatLevelCol;

    @FXML
    private Label weatherWindSpeedlbl;

    @FXML
    private Label wetherSpecialCaseslbl;

    @FXML
    private Label weatherTemplbl;

    public static WeatherAPIDTO weatherAPIDTO;


    WeatherBO weatherBO = (WeatherBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.WEATHER);

    @FXML
    public void initialize() {

        setCellValueFactory();
        setTableItems();
        setDailyInformation();
        setEmailSendingThread();

    }

    private void setEmailSendingThread(){
        new Thread() {
            @Override
            public void run() {
                String nextDate = LocalDate.now().getYear()+ "-" + LocalDate.now().getMonthValue() + "-" + (LocalDate.now().getDayOfMonth()+1);
                try {

                    boolean isUpdatable = weatherBO.isUpdatableDay(LocalDate.now()+"");

                    System.out.println(isUpdatable);
                    if (!isUpdatable){
                        return;
                    }
                    sendWeatherReportsByEmail(weatherBO.getAllEmailsCrew());
                    sendWeatherReportsByEmail(weatherBO.getAllEmailsBoat());

                    System.out.println(weatherBO.addWeatherMgDates(LocalDate.now()+""));
                    weatherBO.addWeatherRecords(new WeatherDTO(
                            null,
                            DashboardWeatherMenuFormController.weatherAPIDTO.getWsTodat_Kmh() ,
                            null ,
                            LocalDate.now()+"" ,
                            LocalTime.now()+"",
                            null
                    ));


                } catch (SQLException e) {
                    System.out.println(e);
                } catch (IOException e) {
                    System.out.println(e);
                }

                this.stop();
            }
        }.start();
    }

    void setTableItems(){
        try {
            ObservableList<WeatherTM> oblist = FXCollections.observableArrayList();

            for (WeatherDTO weatherDTO : weatherBO.getAllWeatherRecords() ) {
                oblist.add(
                        new WeatherTM(
                                weatherDTO.getDate(),
                                weatherDTO.getWindSpeed(),
                                weatherDTO.getSpecialCauses(),
                                weatherDTO.getThreatLevel()
                        )
                );
            }

            weatherTbl.setItems(oblist);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void setDailyInformation(){
        if (weatherAPIDTO.getWsTodat_Kmh() < 37) {
            setRiskLevelImg(stableImg);
        } else if (weatherAPIDTO.getWsTodat_Kmh() < 62) {
            setRiskLevelImg(stableImg);
        } else if (weatherAPIDTO.getWsTodat_Kmh() < 88) {
            setRiskLevelImg(threatImg);
        } else if (weatherAPIDTO.getWsTodat_Kmh() < 118) {
            setRiskLevelImg(threatImg);
        } else {
            setRiskLevelImg(criticleImg);
        }

        weatherWindSpeedlbl.setText(weatherAPIDTO.getWsTodat_Kmh()+" Kmh");
        weatherTemplbl.setText(weatherAPIDTO.getTempToday_C()+" C");

        String specialCauses = null;
        try {
            specialCauses = weatherBO.searchWeatherSpecialCauses(LocalDate.now()+"");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (specialCauses != null){
            wetherSpecialCaseslbl.setText(specialCauses);
            setRiskLevelImg(criticleImg);
        }else {
            wetherSpecialCaseslbl.setText("No");
        }


    }

    void setCellValueFactory() {
        weatherDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        weatherWindSpeedCol.setCellValueFactory(new PropertyValueFactory<>("windSpeed"));
        weatherSpecialCasesCol.setCellValueFactory(new PropertyValueFactory<>("specialCauses"));
        weatherThreatLevelCol.setCellValueFactory(new PropertyValueFactory<>("threatLevel"));
    }

    public void sendWeatherReportsByEmail(List<String> crewEmailAr){
        boolean isMgSend = weatherBO.sendWeatherReportsByEmail(crewEmailAr, weatherAPIDTO);

        if ( isMgSend ){
                System.out.println("All Informed");
        }

    }

    /*public WeatherAPI informationGatherAPI(){
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

    }*/

    void setRiskLevelImg(ImageView currentLevel){
        criticleImg.setVisible(false);
        threatImg.setVisible(false);
        stableImg.setVisible(false);

        currentLevel.setVisible(true);

    }

    @FXML
    void weatherInputDataBtnOnAction(ActionEvent event) {
        DashboardFormController.fullScreen.getChildren().clear();
        try {
            DashboardFormController.fullScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/weather_input_data_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void weatherSearchSpecificDateBtnOnAction(ActionEvent event) {
        DashboardFormController.fullScreen.getChildren().clear();
        try {
            DashboardFormController.fullScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/weather_search_data_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
