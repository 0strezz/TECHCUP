package edu.eci.dosw.tech_cup.dto;

public class ChangeUserRoleRequestDto {
    private String newRole;
    private String performedBy;

    public ChangeUserRoleRequestDto() {

    }

    public ChangeUserRoleRequestDto(String newRole, String performedBy) {
        this.newRole = newRole;
        this.performedBy = performedBy;
    }

    public String getNewRole() {
        return this.newRole;
    }
    public String getPerformedBy() {
        return this.performedBy;
    }

    public void setNewRole(String newRole) {
        this.newRole = newRole;
    }
    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }
}
