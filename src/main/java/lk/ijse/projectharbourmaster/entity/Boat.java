package lk.ijse.projectharbourmaster.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Boat {
    private String boatId;
    private String boatOwner;
    private String boatName;
    private String boatType;
    private int noCrew;
    private double fuelTankCap;
    private double freshWaterCap;
    private double maxWeight;
    private String boatOwnerEmail;

}
