package lk.ijse.projectharbourmaster.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TurnTM {
    String turnId;
    String boatId;
    String capNic;
    int noCrew;
    String outDate;
    String outTime;
    String inDate;
    String inTime;

}
