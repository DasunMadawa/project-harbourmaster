package lk.ijse.projectharbourmaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class WeatherAPIDTO {
    private double tempTomorrow_C;
    private double wsTomorrow_Kmh;
    private double tempToday_C;
    private double wsTodat_Kmh;

}
