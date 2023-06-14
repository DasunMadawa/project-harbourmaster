package lk.ijse.projectharbourmaster.entity;

import javafx.scene.image.WritableImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomEntity {
    private String boatId; // boat
    private String boatOwner;
    private String boatName;
    private String boatType;
    private int boatNoCrew;
    private double fuelTankCap;
    private double freshWaterCap;
    private double maxWeight;
    private String boatOwnerEmail;

    private String dockId; //boat dock
//    private String boatId;
    private String inDate;
    private String outDate;

    private String crewNic; //crew
    private String crewName;
    private WritableImage crewPhoto;
    private String crewBod;
    private String crewAddress;
    private String crewGender;
    private String crewEmail;
    private String crewContact;

//    private String dockId;  //dock
    private int mainDock;
    private int subDock;
    private int dockSide;

    private String employeeNic;  // employee
    private WritableImage employeePhoto;
    private String employeeName;
    private String employeeBod;
    private String employeeAddress;
    private String employeeGender;
    private double employeeSalary;
    private String employeePosition;
    private String employeeEmail;
    private String employeeContact;

    private String fishId;  //fish
    private String fishName;
    private double fishUnitPrice;
    private double fishStock;

    private String stockId; //stock
    private double stockFullCapacity;
    private double stockAvailableCapacity;

//    private String stockId; //stock fish
//    private String fishId;
    private double weight;
    private String date;
    private double unitPriceBought;
    private String addOrRemove;

    private String turnId; //turn
//    private String boatId;
    private String turnCapNIC;
    private String turnCrewCount;
    private String turnOutDate;
    private String turnOutTime;
    private String turnInDate;
    private String turnInTime;

    private String nic; //turn crew
//    private String turnId;

//    private String fishId; // turn fish
//    private String turnId;
//    private String weight;
//    private String date;

    private String userId; // user
    private String userNic;
    private String userName;
    private String userPassword;

//    private String userId; //weather
    private double windSpeed;
    private String specialCauses;
    private String weatherDate;
    private String time;

    private String emailDate; // weather email

    public CustomEntity(String boatId, String boatOwner, String boatName, String boatType, int boatNoCrew, double fuelTankCap, double freshWaterCap, double maxWeight, String dockId) {
        this.boatId = boatId;
        this.boatOwner = boatOwner;
        this.boatName = boatName;
        this.boatType = boatType;
        this.boatNoCrew = boatNoCrew;
        this.fuelTankCap = fuelTankCap;
        this.freshWaterCap = freshWaterCap;
        this.maxWeight = maxWeight;
        this.dockId = dockId;
    }

    public CustomEntity(String stockId, String fishId , double weight, String date, double unitPriceBought, String addOrRemove , String fishName ) {
        this.fishId = fishId;
        this.stockId = stockId;
        this.weight = weight;
        this.date = date;
        this.unitPriceBought = unitPriceBought;
        this.addOrRemove = addOrRemove;
        this.fishName = fishName;

    }

    public CustomEntity(String crewNic, String crewName, String crewAddress, String crewContact , String crewBod ) {
        this.crewNic = crewNic;
        this.crewName = crewName;
        this.crewBod = crewBod;
        this.crewAddress = crewAddress;
        this.crewContact = crewContact;
    }
}
