package edu.eci.dosw.tech_cup.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class ChangeUserRoleRequestDto {

    @NotBlank(message = "New role is required")
    @Size(max = 50, message = "New role must be at most 50 characters long")
    private String newRole;
    
    @NotBlank(message = "Performer is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
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
