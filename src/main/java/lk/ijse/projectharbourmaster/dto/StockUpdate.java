package lk.ijse.projectharbourmaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StockUpdate {
    private String stockId;
    private double weight;
    private boolean add;
    private Fish fish;

}
