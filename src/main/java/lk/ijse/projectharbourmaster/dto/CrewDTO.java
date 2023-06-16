package lk.ijse.projectharbourmaster.dto;

import javafx.scene.image.WritableImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CrewDTO {
    private String nic;
    private String name;
    private WritableImage photo;
    private String dob;
    private String address;
    private String gender;
    private String email;
    private String contact;

    public CrewDTO(String crewNic, String crewName, String crewAddress, String crewContact , String crewBod ) {
        this.nic = crewNic;
        this.name = crewName;
        this.dob = crewBod;
        this.address = crewAddress;
        this.contact = crewContact;
    }

}
