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
    private final String amount;
    private final String payerType;
    private final Long payerId;
    private final String transactionName;
    private final String paymentType;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final Date expiryDate;

    public Payments(String amount, String payerType, Long payerId, String transactionName, String paymentType) {
        this.amount = amount;
        this.payerType = payerType;
        this.payerId = payerId;
        this.transactionName = transactionName;
        this.paymentType = paymentType;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        expiryDate = Date.from(calendar.toInstant());
    }


}
