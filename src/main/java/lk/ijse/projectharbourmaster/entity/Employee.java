package lk.ijse.projectharbourmaster.entity;

import javafx.scene.image.WritableImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private String nic;
    private WritableImage photo;
    private String name;
    private String bod;
    private String address;
    private String gender;
    private double salary;
    private String position;
    private String email;
    private String contact;

}
