package utn.edu.tpfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    String username;
    String pass;

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }

    public void setPassword(String password) {
        this.pass = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
