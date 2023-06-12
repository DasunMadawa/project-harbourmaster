package lk.ijse.projectharbourmaster.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Boat_dock {
    private String dockId;
    private String boatId;
    private String inDate;
    private String outDate;

}
