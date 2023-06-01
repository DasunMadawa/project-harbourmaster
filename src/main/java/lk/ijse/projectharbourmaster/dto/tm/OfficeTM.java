package lk.ijse.projectharbourmaster.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OfficeTM {
    private String nic;
    private String name;
    private String address;
    private String contact;
    private String age;
    private String gender;
    private String position;

}
