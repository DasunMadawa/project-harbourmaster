package lk.ijse.projectharbourmaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnFishDTO {
    private String fishId;
    private String fishName;
    private double qty;

}
