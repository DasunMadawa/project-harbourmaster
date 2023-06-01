package lk.ijse.projectharbourmaster.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StockRecordsTM {
    private String stockId;
    private String fishId;
    private double amount;
    private String date;
    private double unitPriceBought;
    private String action;
    private String fishName;

}
