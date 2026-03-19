package main.java.edu.eci.dosw.tech_cup.dto;

public class ChangeUserRoleRequestDto {
    private String role;
    private String performedBy;

    public ChangeUserRoleRequestDto() {

    }

    public ChangeUserRoleRequestDto(String role, String performedBy) {
        this.role = role;
        this.performedBy = performedBy;
    }

    public String getRole() {
        return this.role;
    }
    public String getPerformedBy() {
        return this.performedBy;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }
}
