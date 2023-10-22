package com.CrimsonBackendDatabase.crimsondb.Payments;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table
@Getter
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String amount;
    private String payerType;
    private Long payerId;
    private String transactionName;
    private String paymentRef;
    private String status;
    private String paymentType;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date expiryDate;

    public Payments() {
    }

    public Payments(String amount, String payerType, Long payerId, String transactionName, String paymentType, String paymentRef, String status) {
        this.amount = amount;
        this.payerType = payerType;
        this.payerId = payerId;
        this.transactionName = transactionName;
        this.paymentType = paymentType;
        this.paymentRef = paymentRef;
        this.status = status;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        expiryDate = Date.from(calendar.toInstant());
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
