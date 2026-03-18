package edu.eci.dosw.tech_cup.model.security;

import java.util.Date;
import java.util.UUID;

public class AuditLog {
    private UUID id;
    private String action;
    private Date timestamp;

    public AuditLog() {
        this.id = UUID.randomUUID();
    }

    public AuditLog(String action, Date timestamp) {
        this.id = UUID.randomUUID();
        this.action = action;
        this.timestamp = timestamp;
    }

    public UUID getId() { return id; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
}
