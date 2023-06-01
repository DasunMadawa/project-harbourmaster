package lk.ijse.projectharbourmaster.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TurnCrewTM {
    private String nic;
    private String name;
    private String address;
    private String contact;
    private String age;
    private Button removeBtn;

}
