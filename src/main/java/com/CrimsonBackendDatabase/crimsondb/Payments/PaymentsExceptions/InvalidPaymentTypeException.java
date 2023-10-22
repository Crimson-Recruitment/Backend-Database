package com.CrimsonBackendDatabase.crimsondb.Payments.PaymentsExceptions;

public class InvalidPaymentTypeException extends Exception{
    public InvalidPaymentTypeException() {
        super("Unsupported payment method!");
    }
}
