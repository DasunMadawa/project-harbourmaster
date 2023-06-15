package lk.ijse.projectharbourmaster.dto;

import javafx.scene.image.WritableImage;
import lk.ijse.projectharbourmaster.dto.tm.CrewTM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomDTO {

    // BoatDTO
    private String boatId;
    private String boatOwner;
    private String boatName;
    private String boatType;
    private int boatNoCrew;
    private double boatFuelCap;
    private double boatWaterCap;
    private double boatMaxWeight;
    private String boatEmail;

    // CastDTO
    private List<String> castEmails;

    // CrewDTO
    private String crewNic;
    private String crewName;
    private WritableImage crewPhoto;
    private String crewDob;
    private String crewAddress;
    private String crewGender;
    private String crewEmail;
    private String crewContact;

    // EmployeeDTO
    private String employeenic;
    private WritableImage employeePhoto;
    private String employeeName;
    private String employeeDob;
    private String employeeAddress;
    private String employeeGender;
    private String employeeSalary;
    private String employeePosition;
    private String employeeEmail;
    private String employeeContact;

    // FishDTO
    private String fishId;
    private String fishName;
    private double fishunitPrice;
    private double fishStock;

    // StockDTO
    private String stockId;
    private double stockFullCapacity;
    private double stockAvailableCapacity;

    // StockUpdateDTO
    private String stockUpdateId;
    private double stockUpdateWeight;
    private boolean stockUpdateAdd;

    // TurnDTO
    private String turnId;
    //        private String boatId;
    private String turnCapNIC;
    private int turnCrewCount;
    private String turnOutDate;
    private String turnoutTime;
    private String inDate;
    private String inTime;
    private List<CrewTM> crewTM;

    private String dockId;

    public CustomDTO(String boatId, String boatOwner, String boatName, String boatType, int boatNoCrew, double fuelTankCap, double freshWaterCap, double maxWeight, String dockId) {
        this.boatId = boatId;
        this.boatOwner = boatOwner;
        this.boatName = boatName;
        this.boatType = boatType;
        this.boatNoCrew = boatNoCrew;
        this.boatFuelCap = fuelTankCap;
        this.boatWaterCap = freshWaterCap;
        this.boatMaxWeight = maxWeight;
        this.dockId = dockId;
    }


}
