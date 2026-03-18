package edu.eci.dosw.tech_cup.model.team;

import edu.eci.dosw.tech_cup.model.enums.InvitationStatus;
import java.util.UUID;

public class Invitation {
    private UUID id;
    private InvitationStatus status;

    public Invitation() {
        this.id = UUID.randomUUID();
        this.status = InvitationStatus.PENDING;
    }

    public void accept() {
        this.status = InvitationStatus.ACCEPTED;
    }

    public void reject() {
        this.status = InvitationStatus.REJECTED;
    }

    public UUID getId() { return id; }
    public InvitationStatus getStatus() { return status; }
    public void setStatus(InvitationStatus status) { this.status = status; }
}
