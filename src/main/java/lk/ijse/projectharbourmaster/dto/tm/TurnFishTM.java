package lk.ijse.projectharbourmaster.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TurnFishTM {
    private String fishId;
    private String fishName;
    private double unitPrice;
    private double qty;
    private Button btn;

}
