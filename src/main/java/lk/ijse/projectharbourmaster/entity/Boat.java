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
    private String noCrew;
    private String fuelTankCap;
    private String freshWaterCap;
    private String maxWeight;
    private String boatOwnerEmail;

}
