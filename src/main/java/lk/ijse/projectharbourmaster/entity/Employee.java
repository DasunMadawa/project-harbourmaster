package lk.ijse.projectharbourmaster.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private String nic;
    private String photo;
    private String name;
    private String bod;
    private String address;
    private String gender;
    private String salary;
    private String position;
    private String email;
    private String contact;

}
