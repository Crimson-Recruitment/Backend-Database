package com.CrimsonBackendDatabase.crimsondb.Payments.PaymentsExceptions;

public class PaymentNotFoundException extends Exception{
    public PaymentNotFoundException(String message) {
        super(message);
    }
}
