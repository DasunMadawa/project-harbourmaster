package lk.ijse.projectharbourmaster.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DockTM {
    private String dockId;
    private String boatId;
    private String inDate;

}
