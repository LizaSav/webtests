package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private  int id;
    private String firstName;
    private String lastName;
    private boolean permission;
    private String email;
    private String password;
    private String address;

}
