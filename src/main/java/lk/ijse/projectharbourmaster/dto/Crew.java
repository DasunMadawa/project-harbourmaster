package lk.ijse.projectharbourmaster.dto;

import javafx.scene.image.WritableImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Crew {
    private String nic;
    private String name;
    private WritableImage photo;
    private String dob;
    private String address;
    private String gender;
    private String email;
    private String contact;

}
