package lk.ijse.projectharbourmaster.dto;

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
    private double fuelCap;
    private double waterCap;
    private double maxWeight;
    private String email;

}
