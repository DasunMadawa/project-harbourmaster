package lk.ijse.projectharbourmaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoatDockDTO {

    private String dockId;
    private String boatId;
    private String inDate;
    private String outDate;

    public BoatDockDTO(String dockId, String boatId, String inDate) {
        this.dockId = dockId;
        this.boatId = boatId;
        this.inDate = inDate;
    }

    public BoatDockDTO(String dockId, String boatId ) {
        this.dockId = dockId;
        this.boatId = boatId;
    }


}
