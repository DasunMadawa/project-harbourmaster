package lk.ijse.projectharbourmaster.entity;

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
    private String boatNoCrew;
    private String fuelTankCap;
    private String freshWaterCap;
    private String maxWeight;
    private String boatOwnerEmail;

    private String dockId; //boat dock
//    private String boatId;
    private String inDate;
    private String outDate;

    private String crewNic; //crew
    private String crewName;
    private String crewPhoto;
    private String crewBod;
    private String crewAddress;
    private String crewGender;
    private String crewEmail;
    private String crewContact;

//    private String dockId;  //dock
    private String mainDock;
    private String subDock;
    private String dockSide;

    private String employeeNic;  // employee
    private String employeePhoto;
    private String employeeName;
    private String employeeBod;
    private String employeeAddress;
    private String employeeGender;
    private String employeeSalary;
    private String employeePosition;
    private String employeeEmail;
    private String employeeContact;

    private String fishId;  //fish
    private String fishName;
    private String fishUnitPrice;
    private String fishStock;

    private String stockId; //stock
    private String stockFullCapacity;
    private String stockAvailableCapacity;

//    private String stockId; //stock fish
//    private String fishId;
    private String weight;
    private String date;
    private String unitPriceBought;
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
    private String windSpeed;
    private String specialCauses;
    private String weatherDate;
    private String time;

    private String emailDate; // weather email

    public CustomEntity(String boatId, String boatOwner, String boatName, String boatType, String boatNoCrew, String fuelTankCap, String freshWaterCap, String maxWeight, String dockId) {
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

}
