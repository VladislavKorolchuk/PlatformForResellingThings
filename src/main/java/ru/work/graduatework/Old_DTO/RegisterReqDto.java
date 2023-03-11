package ru.work.graduatework.Old_DTO;

import lombok.Data;
import ru.work.graduatework.dto.Role;

@Data
public class RegisterReqDto {

    private String username;      // User's email address

    private String password;      // User password

    private String firstName;     // User's name

    private String lastName;      // User's last name

    private String phone;         // User's phone number

    private Role role;            // User's role

    // -------------------- The constructor block ------------------------
    public RegisterReqDto(String username, String password, String firstName, String lastName, String phone, Role role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.role = role;
    }

    // ----------------- block Getter's and Setter's ---------------------
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
