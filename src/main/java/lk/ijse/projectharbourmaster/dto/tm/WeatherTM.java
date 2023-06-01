package lk.ijse.projectharbourmaster.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class WeatherTM {
    private String date;
    private double windSpeed;
    private String specialCauses;
    private String threatLevel;

}
