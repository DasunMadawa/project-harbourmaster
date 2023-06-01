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
import lk.ijse.projectharbourmaster.dto.Weather;
import lk.ijse.projectharbourmaster.dto.WeatherAPI;
import lk.ijse.projectharbourmaster.dto.tm.CrewTM;
import lk.ijse.projectharbourmaster.dto.tm.WeatherTM;
import lk.ijse.projectharbourmaster.model.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

    public static WeatherAPI weatherAPI;

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

                    boolean isUpdatable = WeatherEmailModel.isUpdatableDay(LocalDate.now()+"");

                    System.out.println(isUpdatable);
                    if (!isUpdatable){
                        return;
                    }
                    sendWeatherReportsByEmail(CrewModel.getAllEmails());
                    sendWeatherReportsByEmail(BoatModel.getAllEmails());

                    System.out.println(WeatherEmailModel.insert(LocalDate.now()+""));
                    WeatherModel.insertWeather(new Weather(
                            null,
                            DashboardWeatherMenuFormController.weatherAPI.getWsTodat_Kmh() ,
                            null ,
                            LocalDate.now()+"" ,
                            LocalTime.now()+""
                    ));


                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {

                }
                this.stop();
            }
        }.start();
    }

    void setTableItems(){
        try {
            List<WeatherTM> tableList = WeatherModel.getAll();
            ObservableList<WeatherTM> oblist = FXCollections.observableArrayList();

            for (WeatherTM row : tableList) {
                oblist.add(row);
            }

            weatherTbl.setItems(oblist);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void setDailyInformation(){
        if (weatherAPI.getWsTodat_Kmh() < 37) {
            setRiskLevelImg(stableImg);
        } else if (weatherAPI.getWsTodat_Kmh() < 62) {
            setRiskLevelImg(stableImg);
        } else if (weatherAPI.getWsTodat_Kmh() < 88) {
            setRiskLevelImg(threatImg);
        } else if (weatherAPI.getWsTodat_Kmh() < 118) {
            setRiskLevelImg(threatImg);
        } else {
            setRiskLevelImg(criticleImg);
        }

        weatherWindSpeedlbl.setText(weatherAPI.getWsTodat_Kmh()+" Kmh");
        weatherTemplbl.setText(weatherAPI.getTempToday_C()+" C");

        String specialCauses = null;
        try {
            specialCauses = WeatherModel.searchWeatherSpecialCauses(LocalDate.now()+"");
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
        String nextDate = LocalDate.now().getYear()+ "-" + LocalDate.now().getMonthValue() + "-" + (LocalDate.now().getDayOfMonth()+1);

        double wsTomorrow_kmh = weatherAPI.getWsTomorrow_Kmh();

        for (int i = 0; i < crewEmailAr.size(); i++){
            try {
                if (wsTomorrow_kmh < 37) {
                        EmailModel.sendMail("projectharbourmaster001@gmail.com" , "voyglgayubzuirtf" , crewEmailAr.get(i) , "Stable Wind Speed " + nextDate);
                } else if (wsTomorrow_kmh < 62) {
                        EmailModel.sendMail("projectharbourmaster001@gmail.com" , "voyglgayubzuirtf" , crewEmailAr.get(i) , "Strong Wind Warning "+ nextDate);
                } else if (wsTomorrow_kmh < 88) {
                        EmailModel.sendMail("projectharbourmaster001@gmail.com" , "voyglgayubzuirtf" , crewEmailAr.get(i) , "Gale Warning " + nextDate);
                } else if (wsTomorrow_kmh < 118) {
                        EmailModel.sendMail("projectharbourmaster001@gmail.com" , "voyglgayubzuirtf" , crewEmailAr.get(i) , "Storm Warning " + nextDate);
                } else {
                        EmailModel.sendMail("projectharbourmaster001@gmail.com" , "voyglgayubzuirtf" , crewEmailAr.get(i) , "Hurricane Force Wind Warning " + nextDate);

                }
            } catch (MessagingException e) {
                System.out.println(crewEmailAr.get(i));
                e.printStackTrace();
            }

            if (i == crewEmailAr.size()-1){
                System.out.println("All Informed");
            }
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
