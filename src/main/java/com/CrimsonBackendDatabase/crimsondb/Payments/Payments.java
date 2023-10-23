package com.CrimsonBackendDatabase.crimsondb.Payments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import org.apache.maven.surefire.shared.lang3.RandomStringUtils;
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
    private String status;
    private String paymentType;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date expiryDate;
    @JsonIgnore
    private String random;

    public Payments() {
    }

    public Payments(String amount, String payerType, Long payerId, String transactionName, String paymentType, String status, String random) {
        this.amount = amount;
        this.payerType = payerType;
        this.payerId = payerId;
        this.transactionName = transactionName;
        this.paymentType = paymentType;
        this.random = random;
        this.status = status;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        expiryDate = Date.from(calendar.toInstant());
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
