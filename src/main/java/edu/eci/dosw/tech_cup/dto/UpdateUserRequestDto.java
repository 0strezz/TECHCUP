package main.java.edu.eci.dosw.tech_cup.dto;

public class UpdateUserRequestDto {
    private String name;
    private String email;

    public UpdateUserRequestDto() {

    }

    public UpdateUserRequestDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return this.name;
    }
    public String getEmail() {
        return this.email;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
