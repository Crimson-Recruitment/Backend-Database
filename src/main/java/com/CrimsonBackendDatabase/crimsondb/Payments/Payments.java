package com.CrimsonBackendDatabase.crimsondb.Payments;

import jakarta.persistence.*;
import lombok.Getter;

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

    public Payments(String amount, String payerType, Long payerId, String transactionName) {
        this.amount = amount;
        this.payerType = payerType;
        this.payerId = payerId;
        this.transactionName = transactionName;
    }
}
