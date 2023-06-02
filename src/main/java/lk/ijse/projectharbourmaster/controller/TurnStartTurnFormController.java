package lk.ijse.projectharbourmaster.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lk.ijse.projectharbourmaster.dto.BoatDTO;
import lk.ijse.projectharbourmaster.dto.CrewDTO;
import lk.ijse.projectharbourmaster.dto.TurnDTO;
import lk.ijse.projectharbourmaster.dto.tm.CrewTM;
import lk.ijse.projectharbourmaster.dto.tm.TurnCrewTM;
import lk.ijse.projectharbourmaster.model.BoatModel;
import lk.ijse.projectharbourmaster.model.CrewModel;
import lk.ijse.projectharbourmaster.model.TurnCrewModel;
import lk.ijse.projectharbourmaster.model.TurnModel;
import lk.ijse.projectharbourmaster.util.Validations;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TurnStartTurnFormController {

    @FXML
    public JFXButton startBtn;

    @FXML
    public ComboBox<String> capNICCBOX;

    @FXML
    private JFXTextField crewCounttxt;

    @FXML
    private JFXTextField outDateTxt;

    @FXML
    private JFXTextField outTimeTxt;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton mainBtn;

    @FXML
    private Label turnIdLbl;

    @FXML
    private ComboBox<String> boatIdComboBox;

    @FXML
    private ImageView capImageView;

    @FXML
    private Label capNameLbl;

    @FXML
    private Label capDOBLbl;

    @FXML
    private Label capContactLbl;

    @FXML
    private Label emailLbl;

    @FXML
    private Label boatNameLbl;

    @FXML
    private Label boatTypeLbl;

    @FXML
    private Label noCrewLbl;

    @FXML
    private Label fuelCapLbl;

    @FXML
    private Label waterCapLbl;

    @FXML
    private Label maxWeightLbl;

    @FXML
    private TableView<TurnCrewTM> crewTbl;

    @FXML
    private TableColumn<?, ?> crewNicCol;

    @FXML
    private TableColumn<?, ?> crewNameCol;

    @FXML
    private TableColumn<?, ?> crewAddressCol;

    @FXML
    private TableColumn<?, ?> crewContactCol;

    @FXML
    private TableColumn<?, ?> crewAgeCol;

    @FXML
    private TableColumn<?, ?> crewActionCol;

    @FXML
    private ComboBox<String> crewNICComboBox;

    @FXML
    private JFXButton addBtn;

    private BoatDTO boatDTO;
    private CrewDTO cap;
    private Image defaultImg;
    private ObservableList<TurnCrewTM> obListCrew = FXCollections.observableArrayList();

    @FXML
    void initialize(){
        defaultImg = capImageView.getImage();

        setTurnId();
        setBoatIdsToComboBox();
        setCapNicToComboBox();
        setCrewToComboBox();
        setCellValueFactory();
        setTextFieldValidations();

    }

    private void setTextFieldValidations() {
        Validations.setFocus(crewCounttxt, Validations.intPattern2);
        Validations.setFocus(outDateTxt, Validations.datePattern);
        Validations.setFocus(outTimeTxt, Validations.timePattern);

    }

    private boolean isAllDataValidated() {
        if (crewCounttxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red")) ||
                outDateTxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red")) ||
                outTimeTxt.getFocusColor().equals(javafx.scene.paint.Paint.valueOf("red"))
        ) {

            return false;
        }else if (crewCounttxt.getText().equals("") ||
                outDateTxt.getText().equals("") ||
                outTimeTxt.getText().equals("")
        ){
            return false;
        }
        return true;

    }

    void setCellValueFactory() {
        crewNicCol.setCellValueFactory(new PropertyValueFactory<>("nic"));
        crewNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        crewAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        crewContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        crewAgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        crewActionCol.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));
    }

    private void setCapNicToComboBox() {
        try{
            List<String> allCrewInSea = TurnCrewModel.getAllCrewInSea();
            List<CrewTM> allCrew = CrewModel.getAllForTableFilter();

            ObservableList<String> nicsObList = FXCollections.observableArrayList();

            L1:for (CrewTM crewTM : allCrew) {
                for (String nic : allCrewInSea) {
                    if (crewTM.getNic() != null && crewTM.getNic().equals(nic)){
                        continue L1;
                    }
                }
                for (TurnCrewTM crewTMOnLable:obListCrew) {
                    if (crewTMOnLable.getNic() != null && crewTM.getNic().equals(crewTMOnLable.getNic())){
                        continue L1;
                    }
                }
                nicsObList.add(crewTM.getNic());
            }
            capNICCBOX.setItems(nicsObList);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void setCrewToComboBox() {
        try{
            List<String> allCrewInSea = TurnCrewModel.getAllCrewInSea();
            List<CrewTM> allCrew = CrewModel.getAllForTableFilter();

            ObservableList<String> nicsObList = FXCollections.observableArrayList();

            L1:for (CrewTM crewTM : allCrew) {

                for (String nic : allCrewInSea) {
                    if (crewTM.getNic().equals(nic)){
                        continue L1;
                    }
                }

                if (cap != null && crewTM.getNic().equals(cap.getNic())) {
                    continue L1;
                }

                L2:for (TurnCrewTM turnCrewTM :obListCrew) {
                    if (turnCrewTM.getNic().equals(crewTM.getNic())) {
                        continue L1;
                    }
                }
                nicsObList.add(crewTM.getNic());
            }
            crewNICComboBox.setItems(nicsObList);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void setBoatIdsToComboBox() {
        try {
            List<String> boatIdList = BoatModel.selectAll();
            List<String> allBoatsInSea = TurnModel.getAllBoatsInSea();

            ObservableList<String> obListBoat = FXCollections.observableArrayList();

            L1:for (String boatId : boatIdList) {
                for (String boatIdInSea : allBoatsInSea) {
                    if (boatId.equals(boatIdInSea)){
                        continue L1;
                    }
                }
                obListBoat.add(boatId);
            }

            boatIdComboBox.setItems(obListBoat);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setTurnId() {
        try {
            String nextTurnId = TurnModel.generateNextTurnId();
            turnIdLbl.setText(nextTurnId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addBtnOnAction(ActionEvent event) {
        String crewNic = crewNICComboBox.getValue();

        try {
            CrewDTO crewDTO = CrewModel.searchCrew(crewNic);

            String name = crewDTO.getName();
            String address = crewDTO.getAddress();
            String contact = crewDTO.getContact();
            String dob = crewDTO.getDob();

            int age = LocalDate.now().getYear() - Integer.valueOf(dob.substring(0, 4));

            Button removeBtn = new Button("Remove");
            setRemoveBtnOnAction(removeBtn);

            obListCrew.add(new TurnCrewTM(crewNic , name , address , contact , age+"" , removeBtn));
            crewTbl.setItems(obListCrew);

            setCrewToComboBox();
            setCapNicToComboBox();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void setRemoveBtnOnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {

                int index = crewTbl.getSelectionModel().getSelectedIndex();
                obListCrew.remove(index);

                setCapNicToComboBox();
                setCrewToComboBox();

                crewTbl.refresh();
            }

        });
    }

    @FXML
    void backBtnOnAction(ActionEvent event) {
        DashboardFormController.fullScreen.getChildren().clear();
        DashboardFormController.menuScreen.getChildren().clear();
        try {
            DashboardFormController.fullScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml")));
            DashboardFormController.menuScreen.getChildren().add(FXMLLoader.load(getClass().getResource("/view/dashboard_turn_menu_form.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void boatIdComboBoxOnAction(ActionEvent event) {
        try {
            boatDTO = BoatModel.searchBoat(boatIdComboBox.getValue());

            boatNameLbl.setText(boatDTO.getBoatName());
            boatTypeLbl.setText(boatDTO.getBoatType());
            noCrewLbl.setText(boatDTO.getNoCrew()+"");
            fuelCapLbl.setText(boatDTO.getFuelCap()+"");
            waterCapLbl.setText(boatDTO.getWaterCap()+"");
            maxWeightLbl.setText(boatDTO.getMaxWeight()+"");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void crewNICComboBoxOnAction(ActionEvent event) {

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
    void outDateTxtOnAction(ActionEvent event) {

    }

    @FXML
    void outTimeTxtOnAction(ActionEvent event) {

    }

    private String dateFormateChanger() {
        String date = outDateTxt.getText();

        String[] dateAr = date.split("-");

        return dateAr[2]+"-" + dateAr[1] + "-" + dateAr[0] ;

    }

    @FXML
    public void startBtnOnAction(ActionEvent actionEvent) {

        if (!isAllDataValidated()){
            new Alert(Alert.AlertType.WARNING,
                    "Fill All Data Correctly",
                    ButtonType.OK
            ).show();
            return;
        }

        if (cap == null || boatDTO == null){
            new Alert(Alert.AlertType.WARNING,
                    "SELECT Boat And Captain",
                    ButtonType.OK
            ).show();
            return;

        }

        if (Integer.valueOf(crewCounttxt.getText()) != crewTbl.getItems().size()){
            new Alert(Alert.AlertType.INFORMATION ,
                    "Add Suitable no. of Crew" ,
                    ButtonType.OK
            ).show();
            return;
        }

        String turnId = turnIdLbl.getText();
        int crewCount = Integer.parseInt(crewCounttxt.getText());
        String outDate = dateFormateChanger();
        String outTime = outTimeTxt.getText();

        try {
            List<CrewTM> crewTMList = new ArrayList<>();
            for (TurnCrewTM turnCrewTM:obListCrew){
                crewTMList.add(new CrewTM(turnCrewTM.getNic() ,
                        turnCrewTM.getName() ,
                        turnCrewTM.getAddress() ,
                        turnCrewTM.getAddress() ,
                        turnCrewTM.getAge()
                ));
            }

            boolean isStarted = TurnCrewModel.startTurn(new TurnDTO(turnId ,
                    boatDTO.getBoatId() ,
                    cap.getNic() ,
                    crewCount ,
                    outDate ,
                    outTime ,
                    null ,
                    null ,
                    crewTMList
                    )
            );

            if (isStarted){
                new Alert(Alert.AlertType.INFORMATION ,
                        "Turn Started" ,
                        ButtonType.OK
                ).show();
                backBtnOnAction(actionEvent);
            }else {
                new Alert(Alert.AlertType.INFORMATION ,
                        "Turn Not Started" ,
                        ButtonType.OK
                ).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void crewCounttxtOnAction(ActionEvent actionEvent) {

    }

    @FXML
    public void capNICCBOXOnAction(ActionEvent actionEvent) {
        try {
            if (capNICCBOX.getValue() != null) {
                cap = CrewModel.searchCrew(capNICCBOX.getValue());

                setCrewToComboBox();

                if (cap == null) {
                    return;
                }
            }else {
                return;
            }

            if (cap.getPhoto() != null) {
                capImageView.setImage(cap.getPhoto());
            }else {
                capImageView.setImage(defaultImg);
            }
            capNameLbl.setText(cap.getName());
            capDOBLbl.setText(cap.getDob());

            if (cap.getEmail() != null && !cap.getEmail().isEmpty()){
                emailLbl.setText(cap.getEmail());
            }else {
                emailLbl.setText("Not Inserted");
            }

            if (cap.getContact() != null && !cap.getContact().isEmpty()){
                capContactLbl.setText(cap.getContact());
            }else {
                capContactLbl.setText("Not Inserted");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
