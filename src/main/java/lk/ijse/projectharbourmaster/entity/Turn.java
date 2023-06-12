package lk.ijse.projectharbourmaster.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Turn {
    private String turnId;
    private String boatId;
    private String capNIC;
    private int crewCount;
    private String outDate;
    private String outTime;
    private String inDate;
    private String inTime;

}
