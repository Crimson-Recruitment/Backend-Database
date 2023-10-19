package com.CrimsonBackendDatabase.crimsondb.Utils;

public class PaymentDetails {
    private String amount;
    private String transactionName;
    private String paymentType;
    private String cardNo;
    private String cvv;
    private String expiryMonth;
    private String currency;
    private String country;
    private String firstName;
    private String pin;
    private String billingAddress;
    private String billingCity;
    private String billingCountry;
    private String narration;
    private String mobilePaymentType;

    public PaymentDetails(String transactionName, String paymentType, String amount, String currency, String country, String firstName, String narration, String mobilePaymentType) {
        this.amount = amount;
        this.currency = currency;
        this.country = country;
        this.firstName = firstName;
        this.narration = narration;
        this.mobilePaymentType = mobilePaymentType;
        this.transactionName = transactionName;
        this.paymentType = paymentType;
    }

    public PaymentDetails(String transactionName, String paymentType, String amount, String cardNo, String cvv, String expiryMonth, String currency, String country, String firstName, String pin, String billingAddress, String billingCity, String billingCountry) {
        this.amount = amount;
        this.cardNo = cardNo;
        this.cvv = cvv;
        this.expiryMonth = expiryMonth;
        this.currency = currency;
        this.country = country;
        this.firstName = firstName;
        this.pin = pin;
        this.billingAddress = billingAddress;
        this.billingCity = billingCity;
        this.billingCountry = billingCountry;
        this.transactionName = transactionName;
        this.paymentType = paymentType;
    }
}
