package com.CrimsonBackendDatabase.crimsondb.Company;

import com.CrimsonBackendDatabase.crimsondb.Company.CompanyExceptions.InvalidCompanyException;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.Exceptions.AuthenticationException;
import com.CrimsonBackendDatabase.crimsondb.Exceptions.EmailAlreadyExistsException;
import com.CrimsonBackendDatabase.crimsondb.Users.UsersException.InvalidUserException;
import com.CrimsonBackendDatabase.crimsondb.Utils.CompanyDetails;
import com.CrimsonBackendDatabase.crimsondb.Utils.LoginDetails;
import com.CrimsonBackendDatabase.crimsondb.Utils.PasswordChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "*")
public class CompanyController {
    private final CompanyService companyService;
    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    @PostMapping("/register")
    public HashMap<String, String> companyRegister(@RequestBody Company company) {
        try {
            return companyService.companyRegister(company);
        } catch (EmailAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/login")
    public HashMap<String, String> companyLogin(@RequestBody LoginDetails loginDetails) {
        try {
            return companyService.companyLogin(loginDetails.getEmail(),loginDetails.getPassword());
        } catch (InvalidTokenException | AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/company-details")
    public Company getCompanyDetails(@RequestHeader("Authorization") String accessToken) {
        try {
            return companyService.getCompanyDetails(accessToken);
        } catch (com.CrimsonBackendDatabase.crimsondb.CompanyMessages.CompanyTokenExceptions.InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("company-info/{id}")
    public CompanyDetails getCompanyInfo(@PathVariable("id") Long id) {
        try {
            return companyService.getCompanyInfo(id);
        } catch (InvalidCompanyException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/get-access-token")
    public HashMap<String,String> getAccessToken(@RequestParam("email") String email) {
        try {
            return companyService.getAccessToken(email);
        } catch (InvalidUserException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/update")
    public HashMap<String, String> updateCompanyDetails(@RequestHeader("Authorization") String accessToken,@RequestBody Company company) {
        try {
            return companyService.updateCompanyDetails(accessToken, company);
        } catch (com.CrimsonBackendDatabase.crimsondb.CompanyMessages.CompanyTokenExceptions.InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/change-password")
    public HashMap<String, String> changePassword(@RequestHeader("Authorization") String accessToken, @RequestBody PasswordChange passwordChange) {
        try {
            return  companyService.changePassword(accessToken,passwordChange);
        } catch (AuthenticationException |
                 com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/validate-email")
    public HashMap<String, String> validateEmail(@RequestHeader("Authorization") String accessToken) {
        try {
            return companyService.validateEmail(accessToken);
        } catch (com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/validate-phone-number")
    public HashMap<String, String> validatePrimaryPhoneNumber(@RequestHeader("Authorization") String accessToken) {
        try {
            return  companyService.validatePrimaryPhoneNumber(accessToken);
        } catch (com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }
}
