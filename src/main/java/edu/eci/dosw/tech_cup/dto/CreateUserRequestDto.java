package main.java.edu.eci.dosw.tech_cup.dto;

public class CreateUserRequestDto {
    private Strin name;
    private Strin email;
    private String password;

    public CreateUserRequestDto() {

    }

    public CreateUserRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.mail;
    }

    public String password() {
        return this.password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
