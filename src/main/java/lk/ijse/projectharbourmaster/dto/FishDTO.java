package lk.ijse.projectharbourmaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FishDTO {
    private String fishId;
    private String fishName;
    private double unitPrice;
    private double stock;

}
