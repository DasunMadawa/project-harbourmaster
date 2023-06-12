package lk.ijse.projectharbourmaster.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dock {
    private String dockId;
    private String mainDock;
    private String subDock;
    private String dockSide;

}
