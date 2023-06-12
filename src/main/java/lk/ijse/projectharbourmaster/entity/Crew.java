package lk.ijse.projectharbourmaster.entity;

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
    private String bod;
    private String address;
    private String gender;
    private String email;
    private String contact;

    public Crew(String nic, String name, String address, String contact , String bod) {
        this.nic = nic;
        this.name = name;
        this.bod = bod;
        this.address = address;
        this.contact = contact;
    }
}
