package com.CrimsonBackendDatabase.crimsondb.Payments;

import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyToken;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenService;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserToken;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenService;
import com.CrimsonBackendDatabase.crimsondb.Utils.PaymentDetails;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.flutterwave.rave.java.payload.*;
import com.flutterwave.rave.java.entry.*;

import java.net.UnknownHostException;
import java.util.*;

@Service
public class PaymentsService {
    @Value("${fl.public_key}")
    private String flPublicKey;
    @Value("${fl.encryption_key}")
    private String flEncryptionKey;
    @Value("${fl.secret_key}")
    private String flSecretKey;
    private final UserTokenService userTokenService;
    private final CompanyTokenService companyTokenService;
    private final PaymentsRepository paymentsRepository;

    public PaymentsService(UserTokenService userTokenService, CompanyTokenService companyTokenService, PaymentsRepository paymentsRepository){
        this.companyTokenService =  companyTokenService;
        this.userTokenService = userTokenService;
        this.paymentsRepository = paymentsRepository;
    }
    public HashMap<String, Object> userSubscribe(String accessToken, PaymentDetails paymentDetails) throws InvalidTokenException, UnknownHostException, InvalidPaymentTypeException {
        Optional<UserToken> userToken = userTokenService.findUserToken(accessToken);
        if(userToken.isPresent()) {
            boolean isValid = userTokenService.validateToken(accessToken, String.valueOf(userToken.get().getUsers().getId()));
            if(isValid) {
                String userEmail = "";
                String userPhoneNumber = "";
                if(Objects.equals(paymentDetails.getEmail(), "") || Objects.equals(paymentDetails.getEmail() ,null)) {
                    userEmail = userToken.get().getUsers().getEmail();
                } else {
                    userEmail = paymentDetails.getEmail();
                }
                if (Objects.equals(paymentDetails.getPhoneNumber(), "") ||Objects.equals(paymentDetails.getPhoneNumber(), null)) {
                    userPhoneNumber = userToken.get().getUsers().getPhoneNumber();
                } else {
                    userPhoneNumber = paymentDetails.getPhoneNumber();
                }
                if(paymentDetails.getPaymentType().equalsIgnoreCase("card")) {
                    String result = cardPayment(
                            paymentDetails.getCardNo(),
                            paymentDetails.getCvv(),
                            paymentDetails.getExpiryMonth(),
                            paymentDetails.getExpiryYear(),
                            paymentDetails.getCurrency(),
                            paymentDetails.getCountry(),
                            paymentDetails.getAmount(),
                            userEmail,
                            userPhoneNumber,
                            paymentDetails.getFirstName(),
                            paymentDetails.getPin(),
                            paymentDetails.getBillingAddress(),
                            paymentDetails.getBillingCity(),
                            paymentDetails.getBillingCountry()
                    );
                    HashMap<String, Object> data = new Gson().fromJson(result, new TypeToken<HashMap<String, Object>>(){}.getType());
                    data.put("result", "success");
                    return data;
                }else if(paymentDetails.getPaymentType().equalsIgnoreCase("mobilemoney")) {
                    String result = mobileMoneyPayment(
                            paymentDetails.getCurrency(),
                            paymentDetails.getAmount(),
                            userPhoneNumber,
                            userEmail,
                            paymentDetails.getFirstName(),
                            paymentDetails.getNarration(),
                            paymentDetails.getCountry(),
                            paymentDetails.getMobilePaymentType()
                    );
                    HashMap<String, Object> data  = new Gson().fromJson(result, new TypeToken<HashMap<String, Object>>(){}.getType());
                    data.put("result", "success");
                    data.put("Message",result);
                    return data;
                } else {
                    throw new InvalidPaymentTypeException();
                }

            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }
    };
    public HashMap<String, Object> companySubscribe(String accessToken, PaymentDetails paymentDetails) throws InvalidTokenException, UnknownHostException, InvalidPaymentTypeException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if(companyToken.isPresent()) {
            boolean isValid = userTokenService.validateToken(accessToken, String.valueOf(companyToken.get().getCompany().getId()));
            if(isValid) {
                String userEmail = "";
                String userPhoneNumber = "";
                if(Objects.equals(paymentDetails.getEmail(), "") || Objects.equals(paymentDetails.getEmail() ,null)) {
                    userEmail = companyToken.get().getCompany().getEmail();
                } else {
                    userEmail = paymentDetails.getEmail();
                }
                if (Objects.equals(paymentDetails.getPhoneNumber(), "") ||Objects.equals(paymentDetails.getPhoneNumber(), null)) {
                    userPhoneNumber = companyToken.get().getCompany().getPrimaryPhoneNumber();
                } else {
                    userPhoneNumber = paymentDetails.getPhoneNumber();
                }
                if(paymentDetails.getPaymentType().equalsIgnoreCase("card")) {
                    String result = cardPayment(
                            paymentDetails.getCardNo(),
                            paymentDetails.getCvv(),
                            paymentDetails.getExpiryMonth(),
                            paymentDetails.getExpiryYear(),
                            paymentDetails.getCurrency(),
                            paymentDetails.getCountry(),
                            paymentDetails.getAmount(),
                            userEmail,
                            userPhoneNumber,
                            paymentDetails.getFirstName(),
                            paymentDetails.getPin(),
                            paymentDetails.getBillingAddress(),
                            paymentDetails.getBillingCity(),
                            paymentDetails.getBillingCountry()
                    );
                    HashMap<String, Object> data = new Gson().fromJson(result, new TypeToken<HashMap<String, Object>>(){}.getType());
                    data.put("result", "success");
                    return data;
                }else if(paymentDetails.getPaymentType().equalsIgnoreCase("mobilemoney")) {
                    String result = mobileMoneyPayment(
                            paymentDetails.getCurrency(),
                            paymentDetails.getAmount(),
                            userPhoneNumber,
                            userEmail,
                            paymentDetails.getFirstName(),
                            paymentDetails.getNarration(),
                            paymentDetails.getCountry(),
                            paymentDetails.getMobilePaymentType()
                    );
                    HashMap<String, Object> data  = new Gson().fromJson(result, new TypeToken<HashMap<String, Object>>(){}.getType());
                    data.put("result", "success");
                    return data;
                }else {
                    throw new InvalidPaymentTypeException();
                }
            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }
    };
    public List<Payments> viewUserPayments(String accessToken) throws InvalidTokenException {
        Optional<UserToken> userToken = userTokenService.findUserToken(accessToken);
        if(userToken.isPresent()) {
            boolean isValid = userTokenService.validateToken(accessToken, String.valueOf(userToken.get().getUsers().getId()));
            if(isValid) {
                Optional<List<Payments>> payments = paymentsRepository.findPaymentsByPayerId(userToken.get().getUsers().getId());
                return payments.orElseGet(ArrayList::new);
            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }
    };
    public List<Payments> viewCompanyPayments(String accessToken) throws InvalidTokenException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if(companyToken.isPresent()) {
            boolean isValid = userTokenService.validateToken(accessToken, String.valueOf(companyToken.get().getCompany().getId()));
            if(isValid) {
            Optional<List<Payments>> payments = paymentsRepository.findPaymentsByPayerId(companyToken.get().getCompany().getId());
            return payments.orElseGet(ArrayList::new);
            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }
    };

    private String cardPayment(
            String cardNo,
            String cvv,
            String expiryMonth,
            String expiryYear,
            String currency,
            String country,
            String amount,
            String email,
            String phoneNumber,
            String firstName,
            String pin,
            String billingAddress,
            String billingCity,
            String billingCountry
    ) throws UnknownHostException, JSONException {
        cardPayment cardPayment = new cardPayment();
        cardLoad cardload = new cardLoad();
        cardload.setPublic_key(flPublicKey);
        cardload.setCardno(cardNo);
        cardload.setCvv(cvv);
        cardload.setExpirymonth(expiryMonth);
        cardload.setExpiryyear(expiryYear);
        cardload.setCurrency(currency);
        cardload.setCountry(country);
        cardload.setAmount(amount);
        cardload.setEmail(email);
        cardload.setSecret_key(flSecretKey);
        cardload.setEncryption_key(flEncryptionKey);
        cardload.setPhonenumber(phoneNumber);
        cardload.setFirstname(firstName);

        String response = cardPayment.doflwcardpayment(cardload);

        JSONObject myObject = new JSONObject(response);

        if (myObject.optString("suggested_auth").equals("PIN")) {
            //get PIN fom customer
            cardload.setPin(pin);
            cardload.setSuggested_auth("PIN");
            String response_one = cardPayment.doflwcardpayment(cardload);

            JSONObject iObject = new JSONObject(response_one);
            JSONObject Object = iObject.optJSONObject("data");

            String transaction_reference = Object.optString("flwRef");

            validateCardCharge validatecardcharge = new validateCardCharge();
            validateCardPayload validatecardpayload = new validateCardPayload();
            validatecardpayload.setPBFPubKey(flPublicKey);
            validatecardpayload.setTransaction_reference(transaction_reference);

            response = validatecardcharge.doflwcardvalidate(validatecardpayload);
        } else if (myObject.optString("suggested_auth").equals("NOAUTH_INTERNATIONAL")) {
            //billing info - billingzip, billingcity, billingaddress, billingstate, billingcountry
            cardload.setBillingaddress(billingAddress);
            cardload.setBillingcity(billingCity);
            cardload.setBillingcountry(billingCountry);
            cardload.setSuggested_auth("NOAUTH_INTERNATIONAL");
            String response_one = cardPayment.doflwcardpayment(cardload);

            JSONObject iObject = new JSONObject(response_one);
            JSONObject Object = iObject.optJSONObject("data");

            String transaction_reference = Object.optString("flwRef");

            validateCardCharge validatecardcharge = new validateCardCharge();
            validateCardPayload validatecardpayload = new validateCardPayload();
            validatecardpayload.setPBFPubKey(flPublicKey);
            validatecardpayload.setTransaction_reference(transaction_reference);

            response = validatecardcharge.doflwcardvalidate(validatecardpayload);
        } else if (myObject.optString("authurl") != "N/A") {
            //load the url in an IFRAME
        }
        return response;
    }

    private String mobileMoneyPayment(
            String currency,
            String amount,
            String phoneNumber,
            String email,
            String firstName,
            String narration,
            String country,
            String paymentType

    ) throws UnknownHostException {
        mobileMoney mobileMoney = new mobileMoney();
        mobilemoneyPayload mobilemoneyPayload = new mobilemoneyPayload();
        mobilemoneyPayload.setPBFPubKey(flPublicKey);
        mobilemoneyPayload.setCurrency(currency);
        mobilemoneyPayload.setAmount(amount);
        mobilemoneyPayload.setPhonenumber(phoneNumber);
        mobilemoneyPayload.setEmail(email);
        mobilemoneyPayload.setFirstname(firstName);
        mobilemoneyPayload.setNetwork(narration);
        mobilemoneyPayload.setEncryption_key(flEncryptionKey);
        mobilemoneyPayload.setSecret_key(flSecretKey);
        mobilemoneyPayload.setCountry(country);
        mobilemoneyPayload.setPublic_key(flPublicKey);
        mobilemoneyPayload.setIs_mobile_money_ug(1);
        mobilemoneyPayload.setPayment_type(paymentType);
      //if split payment set subaccount values

        String response = mobileMoney.domobilemoney(mobilemoneyPayload);
        return response;
    }

}

