package lk.ijse.projectharbourmaster.dto;

import lk.ijse.projectharbourmaster.dto.tm.CrewTM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<CrewTM> crewTM;

}
