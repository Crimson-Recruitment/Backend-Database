package com.CrimsonBackendDatabase.crimsondb.Utils;

import lombok.Getter;

@Getter
public class PaymentDetails {
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
    private String expiryYear;
    private String phoneNumber = "";
    private String email = "";

    public PaymentDetails() {
    }

    public PaymentDetails(String transactionName, String paymentType, String amount, String currency, String country, String firstName, String narration, String mobilePaymentType, String phoneNumber, String email) {
        this.currency = currency;
        this.country = country;
        this.firstName = firstName;
        this.narration = narration;
        this.mobilePaymentType = mobilePaymentType;
        this.transactionName = transactionName;
        this.paymentType = paymentType;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public PaymentDetails(String transactionName, String expiryYear, String paymentType, String amount, String cardNo, String cvv, String expiryMonth, String currency, String country, String firstName, String pin, String billingAddress, String billingCity, String billingCountry) {
        this.cardNo = cardNo;
        this.cvv = cvv;
        this.expiryMonth = expiryMonth;
        this.currency = currency;
        this.country = country;
        this.firstName = firstName;
        this.expiryYear = expiryYear;
        this.pin = pin;
        this.billingAddress = billingAddress;
        this.billingCity = billingCity;
        this.billingCountry = billingCountry;
        this.transactionName = transactionName;
        this.paymentType = paymentType;
    }

}
