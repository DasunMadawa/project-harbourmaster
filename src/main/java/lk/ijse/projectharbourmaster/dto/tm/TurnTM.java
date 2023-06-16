package lk.ijse.projectharbourmaster.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TurnTM {
    private String turnId;
    private String boatId;
    private String capNic;
    private int noCrew;
    private String outDate;
    private String outTime;
    private String inDate;
    private String inTime;

}
