package com.CrimsonBackendDatabase.crimsondb.Payments;
import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.Company.CompanyRepository;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyToken;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenService;
import com.CrimsonBackendDatabase.crimsondb.Payments.PaymentsExceptions.InvalidPaymentTypeException;
import com.CrimsonBackendDatabase.crimsondb.Payments.PaymentsExceptions.InvalidTierTypeException;
import com.CrimsonBackendDatabase.crimsondb.Payments.PaymentsExceptions.PaymentNotFoundException;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserToken;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenService;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import com.CrimsonBackendDatabase.crimsondb.Users.UsersException.InvalidUserException;
import com.CrimsonBackendDatabase.crimsondb.Users.UsersRepository;
import com.CrimsonBackendDatabase.crimsondb.Utils.PaymentDetails;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.transaction.Transactional;
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
    private final UsersRepository usersRepository;
    private final CompanyRepository companyRepository;

    public PaymentsService(UserTokenService userTokenService, CompanyTokenService companyTokenService, PaymentsRepository paymentsRepository, UsersRepository usersRepository, CompanyRepository companyRepository){
        this.companyTokenService =  companyTokenService;
        this.userTokenService = userTokenService;
        this.paymentsRepository = paymentsRepository;
        this.usersRepository = usersRepository;
        this.companyRepository = companyRepository;
    }
    public HashMap<String, Object> userSubscribe(String accessToken, PaymentDetails paymentDetails) throws InvalidTokenException, UnknownHostException, InvalidPaymentTypeException, InvalidTierTypeException {
        Optional<UserToken> userToken = userTokenService.findUserToken(accessToken);
        if(userToken.isPresent()) {
            boolean isValid = userTokenService.validateToken(accessToken, String.valueOf(userToken.get().getUsers().getId()));
            if(isValid) {
                String userEmail = "";
                String userPhoneNumber = "";
                String amount = "";
                if (paymentDetails.getTransactionName().equals("Premium")) {
                    amount = "6000";
                } else {
                    throw new InvalidTierTypeException("This tier doesn't exist!");
                }
                userEmail = userToken.get().getUsers().getEmail();
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
                            amount,
                            userEmail,
                            userPhoneNumber,
                            paymentDetails.getFirstName(),
                            paymentDetails.getPin(),
                            paymentDetails.getBillingAddress(),
                            paymentDetails.getBillingCity(),
                            paymentDetails.getBillingCountry(),
                            "http://localhost:8081/payments/confirm-user-payment"
                    );
                    HashMap<String, Object> data = new Gson().fromJson(result, new TypeToken<HashMap<String, Object>>(){}.getType());
                    HashMap<String, Object> responseData = (HashMap<String, Object>) data.get("data");
                    Payments payment = new Payments(
                            amount,
                            "User",
                            userToken.get().getUsers().getId(),
                            paymentDetails.getTransactionName(),
                            paymentDetails.getPaymentType(),
                            (String) data.get("status"));
                    data.put("result", "success");
                    paymentsRepository.save(payment);
                    data.put("result", "success");
                    return data;
                }else if(paymentDetails.getPaymentType().equalsIgnoreCase("mobilemoney")) {
                    String result = mobileMoneyPayment(
                            paymentDetails.getCurrency(),
                            amount,
                            userPhoneNumber,
                            userEmail,
                            paymentDetails.getFirstName(),
                            paymentDetails.getNarration(),
                            paymentDetails.getCountry(),
                            paymentDetails.getMobilePaymentType(),
                            "http://localhost:8081/payments/confirm-user-payment"
                    );
                    return new Gson().fromJson(result, new TypeToken<HashMap<String, Object>>(){}.getType());
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
    public HashMap<String, Object> companySubscribe(String accessToken, PaymentDetails paymentDetails) throws InvalidTokenException, UnknownHostException, InvalidPaymentTypeException, InvalidTierTypeException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if(companyToken.isPresent()) {
            boolean isValid = companyTokenService.validateToken(accessToken, String.valueOf(companyToken.get().getCompany().getId()));
            if(isValid) {
                String userEmail = "";
                String userPhoneNumber = "";
                String amount = "";
                switch (paymentDetails.getTransactionName()){
                    case "Starter":
                        amount = "3000";
                        break;
                    case "Pro":
                        amount = "5000";
                        break;
                    case "Enterprise":
                        amount = "10000";
                        break;
                    default:
                        throw new InvalidTierTypeException("This tier doesn't exist!");
                }
                userEmail = companyToken.get().getCompany().getEmail();
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
                            amount,
                            userEmail,
                            userPhoneNumber,
                            paymentDetails.getFirstName(),
                            paymentDetails.getPin(),
                            paymentDetails.getBillingAddress(),
                            paymentDetails.getBillingCity(),
                            paymentDetails.getBillingCountry(),
                            "http://localhost:8081/payments/confirm-company-payment"
                    );
                    HashMap<String, Object> data = new Gson().fromJson(result, new TypeToken<HashMap<String, Object>>(){}.getType());
                    return data;
                }else if(paymentDetails.getPaymentType().equalsIgnoreCase("mobilemoney")) {
                    String result = mobileMoneyPayment(
                            paymentDetails.getCurrency(),
                            amount,
                            userPhoneNumber,
                            userEmail,
                            paymentDetails.getFirstName(),
                            paymentDetails.getNarration(),
                            paymentDetails.getCountry(),
                            paymentDetails.getMobilePaymentType(),
                            "http://localhost:8081/payments/confirm-company-payment"
                    );

                    HashMap<String, Object> data = new Gson().fromJson(result, new TypeToken<HashMap<String, Object>>(){}.getType());
                    Payments payment = new Payments(
                            amount,
                            "Company",
                            companyToken.get().getCompany().getId(),
                            paymentDetails.getTransactionName(),
                            paymentDetails.getPaymentType(),
                            data.get("status").toString());
                    paymentsRepository.save(payment);
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
                Optional<List<Payments>> payments = paymentsRepository.findPaymentsByPayerId(userToken.get().getUsers().getId(), "User");
                return payments.orElseGet(ArrayList::new);
            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }
    };

    @Transactional
    public HashMap<String, Object> confirmUserPayments(String response) throws PaymentNotFoundException, InvalidTokenException, InvalidUserException {
            HashMap<String, Object> data  = new Gson().fromJson(response, new TypeToken<HashMap<String, Object>>(){}.getType());
            if (data.get("status") == "success") {
                HashMap<String, Object> mo = (HashMap<String, Object>) data.get("data");
                Optional<Users> user = usersRepository.findUsersByEmail((String) mo.get("customer.email"));
                if(user.isPresent()) {
                    Optional<Payments> payment = paymentsRepository.findPaymentsByPayerId(user.get().getId(), "User");
                    if (payment.isPresent()){
                        payment.get().setStatus(data.get("status").toString());
                    } else {
                        throw new PaymentNotFoundException("This payment doesn't exist!");
                    }
                } else {
                    throw new InvalidUserException();
                }
                return mo;
            } else {
                return data;
            }

    };
    public HashMap<String, Object> confirmCompanyPayments(String response) throws PaymentNotFoundException, InvalidTokenException, InvalidUserException {
            HashMap<String, Object> data  = new Gson().fromJson(response, new TypeToken<HashMap<String, Object>>(){}.getType());
        if (data.get("status") == "success") {
            HashMap<String, Object> mo = (HashMap<String, Object>) data.get("data");
            Optional<Company> company = companyRepository.findCompanyByEmail((String) mo.get("customer.email"));
            if(company.isPresent()) {
                Payments payment = new Payments(
                        mo.get("amount").toString(),
                        "Company",
                        company.get().getId(),
                        "Premium",
                        mo.get("paymentType").toString(),
                        data.get("status").toString());
            } else {
                throw new InvalidUserException();
            }
            return mo;
        } else {
            return data;
        }

    };

    public HashMap<String, Object> payPostJob(String accessToken, PaymentDetails paymentDetails) throws UnknownHostException, InvalidPaymentTypeException, InvalidTokenException, InvalidTierTypeException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if(companyToken.isPresent()) {
            boolean isValid = companyTokenService.validateToken(accessToken, String.valueOf(companyToken.get().getCompany().getId()));
            if(isValid) {
                String userEmail = "";
                String userPhoneNumber = "";
                String amount = "";
                switch (companyToken.get().getCompany().getTier()){
                    case "Free":
                        amount = "10000";
                        break;
                    case "Starter":
                        amount = "5000";
                        break;
                    case "Pro":
                        amount = "3000";
                        break;
                    case "Enterprise":
                        amount = "2000";
                        break;
                    default:
                        throw new InvalidTierTypeException("This tier doesn't exist!");
                }
                userEmail = companyToken.get().getCompany().getEmail();
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
                            amount,
                            userEmail,
                            userPhoneNumber,
                            paymentDetails.getFirstName(),
                            paymentDetails.getPin(),
                            paymentDetails.getBillingAddress(),
                            paymentDetails.getBillingCity(),
                            paymentDetails.getBillingCountry(),
                            "http://localhost:8081/payments/confirm-company-payment"
                    );
                    HashMap<String, Object> data = new Gson().fromJson(result, new TypeToken<HashMap<String, Object>>(){}.getType());
                    data.put("result", "success");
                    return data;
                }else if(paymentDetails.getPaymentType().equalsIgnoreCase("mobilemoney")) {
                    String result = mobileMoneyPayment(
                            paymentDetails.getCurrency(),
                            amount,
                            userPhoneNumber,
                            userEmail,
                            paymentDetails.getFirstName(),
                            paymentDetails.getNarration(),
                            paymentDetails.getCountry(),
                            paymentDetails.getMobilePaymentType(),
                            "http://localhost:8081/payments/confirm-company-payment"
                    );

                    return new Gson().fromJson(result, new TypeToken<HashMap<String, Object>>(){}.getType());
                }else {
                    throw new InvalidPaymentTypeException();
                }
            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }
    }

    public List<Payments> viewCompanyPayments(String accessToken) throws InvalidTokenException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if(companyToken.isPresent()) {
            boolean isValid = userTokenService.validateToken(accessToken, String.valueOf(companyToken.get().getCompany().getId()));
            if(isValid) {
            Optional<List<Payments>> payments = paymentsRepository.findPaymentsByPayerId(companyToken.get().getCompany().getId(), "Company");
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
            String billingCountry,
            String redirect_url
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
        cardload.setRedirect_url(redirect_url);
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
            String paymentType,
            String redirect_url

    ) throws UnknownHostException {
        mobileMoney mobileMoney = new mobileMoney();
        mobilemoneyPayload mobilemoneyPayload = new mobilemoneyPayload();
        mobilemoneyPayload.setPBFPubKey(flPublicKey);
        mobilemoneyPayload.setCurrency(currency);
        mobilemoneyPayload.setAmount(amount);
        mobilemoneyPayload.setPhonenumber(phoneNumber);
        mobilemoneyPayload.setEmail(email);
        mobilemoneyPayload.setRedirect_url(redirect_url);
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

