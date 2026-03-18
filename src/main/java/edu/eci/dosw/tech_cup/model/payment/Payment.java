package edu.eci.dosw.tech_cup.model.payment;

import edu.eci.dosw.tech_cup.model.enums.PaymentStatus;
import java.util.Date;
import java.util.UUID;

public class Payment {
    private UUID id;
    private Date date;
    private String receiptUrl;
    private PaymentStatus status;

    public Payment() {
        this.id = UUID.randomUUID();
        this.status = PaymentStatus.PENDING;
    }

    public Payment(Date date, String receiptUrl) {
        this.id = UUID.randomUUID();
        this.date = date;
        this.receiptUrl = receiptUrl;
        this.status = PaymentStatus.PENDING;
    }

    public void approve() {
        this.status = PaymentStatus.APPROVED;
    }

    public void reject() {
        this.status = PaymentStatus.REJECTED;
    }

    public UUID getId() { return id; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getReceiptUrl() { return receiptUrl; }
    public void setReceiptUrl(String receiptUrl) { this.receiptUrl = receiptUrl; }
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
}
