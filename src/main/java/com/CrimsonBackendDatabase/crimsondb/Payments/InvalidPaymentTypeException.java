package com.CrimsonBackendDatabase.crimsondb.Payments;

public class InvalidPaymentTypeException extends Exception{
    public InvalidPaymentTypeException() {
        super("Unsupported payment method!");
    }
}
