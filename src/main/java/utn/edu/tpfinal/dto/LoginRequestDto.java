package utn.edu.tpfinal.dto;

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
