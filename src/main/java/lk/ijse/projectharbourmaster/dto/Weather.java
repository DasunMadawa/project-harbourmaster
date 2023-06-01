package lk.ijse.projectharbourmaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Weather {
    private String userId;
    private double windSpeed;
    private String specialCauses;
    private String date;
    private String time;
}
