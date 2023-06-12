package lk.ijse.projectharbourmaster.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock_fish {
    private String stockId;
    private String fishId;
    private double weight;
    private String date;
    private double unitPriceBought;
    private String addOrRemove;

}
