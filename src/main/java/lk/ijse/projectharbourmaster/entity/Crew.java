package lk.ijse.projectharbourmaster.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Crew {
    private String nic;
    private String name;
    private String photo;
    private String bod;
    private String address;
    private String gender;
    private String email;
    private String contact;

}
