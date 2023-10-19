package com.CrimsonBackendDatabase.crimsondb.Payments;

import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.Utils.PaymentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/payments")
public class PaymentsController {

    private final PaymentsService paymentsService;

    @Autowired
    public PaymentsController(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }
    @PostMapping("/user-subscribe")
    public HashMap<String, String> userSubscribe(@RequestHeader("Authorization") String accessToken, @RequestBody PaymentDetails paymentDetails) {
        try {
            return paymentsService.userSubscribe(accessToken,paymentDetails);
        } catch (InvalidTokenException | UnknownHostException | InvalidPaymentTypeException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/company-subscribe")
    public HashMap<String, String> companySubscribe(@RequestHeader("Authorization") String accessToken, PaymentDetails paymentDetails) {
        try {
            return paymentsService.companySubscribe(accessToken,paymentDetails);
        } catch (InvalidTokenException | UnknownHostException | InvalidPaymentTypeException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/user-payments")
    public List<Payments> userPayments(@RequestHeader("Authorization") String accessToken) {
        try {
            return paymentsService.viewUserPayments(accessToken);
        } catch (InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/company-payments")
    private List<Payments> companyPayments(@RequestHeader("Authorization") String accessToken) {
        try {
            return paymentsService.viewCompanyPayments(accessToken);
        } catch (InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }

}
