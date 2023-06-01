package lk.ijse.projectharbourmaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Stock {
    private String stockId;
    private double fullCapacity;
    private double availableCapacity;


}
