package com.CrimsonBackendDatabase.crimsondb.Company;

import com.CrimsonBackendDatabase.crimsondb.Company.CompanyExceptions.InvalidCompanyException;
import com.CrimsonBackendDatabase.crimsondb.CompanyImages.CompanyImages;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyToken;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenService;
import com.CrimsonBackendDatabase.crimsondb.Exceptions.AuthenticationException;
import com.CrimsonBackendDatabase.crimsondb.Exceptions.EmailAlreadyExistsException;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.Utils.CompanyDetails;
import com.CrimsonBackendDatabase.crimsondb.Utils.PasswordChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    @Autowired
    private CompanyTokenService companyTokenService;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }
    public HashMap<String, String> companyRegister(Company company) throws EmailAlreadyExistsException {
        Optional<Company> checkCompany = companyRepository.findCompanyByEmail(company.getEmail());
        if(checkCompany.isPresent()) {
            throw new EmailAlreadyExistsException();
        } else {
            company.setPassword(encoder.encode(company.getPassword()));
            companyRepository.save(company);
            String accessToken = companyTokenService.generateToken(company);
            HashMap<String, String> result = new HashMap<String, String>();
            result.put("result", "success");
            return result;
        }
    };
    public HashMap<String, String> companyLogin(String email, String password) throws com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenExceptions.InvalidTokenException, AuthenticationException {
        Optional<Company> user = companyRepository.findCompanyByEmail(email);
        if (user.isPresent()) {
            boolean val =  encoder.matches(password,user.get().getPassword());
            if(val){
                String token = companyTokenService.regenerateToken(user.get());
                HashMap<String, String> result = new HashMap<String, String>();
                result.put("result","success");
                return result;
            } else {
                throw new AuthenticationException();
            }
        } else {
            throw new AuthenticationException();
        }

    };

    public CompanyDetails getCompanyInfo(Long id) throws InvalidCompanyException {
        Optional<Company> company = companyRepository.findById(id);
        if(company.isPresent()){
            return new CompanyDetails(company.get().getCompanyName(),company.get().getCategory(),company.get().getProfileImage(),new ArrayList<CompanyImages>(company.get().getCompanyImages()),company.get().getOverview(),company.get().getPrimaryPhoneNumber(),company.get().getSecondaryPhoneNumber());
        } else {
            throw new InvalidCompanyException();
        }
    }

    public Company getCompanyDetails(String accessToken) throws com.CrimsonBackendDatabase.crimsondb.CompanyMessages.CompanyTokenExceptions.InvalidTokenException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if(companyToken.isPresent()) {
            boolean isValid = companyTokenService.validateToken(accessToken, String.valueOf(companyToken.get().getCompany().getId()));
            if(isValid) {
                return companyToken.get().getCompany();
            } else {
                throw new com.CrimsonBackendDatabase.crimsondb.CompanyMessages.CompanyTokenExceptions.InvalidTokenException();
            }
        } else {
            throw new com.CrimsonBackendDatabase.crimsondb.CompanyMessages.CompanyTokenExceptions.InvalidTokenException();
        }
    }

    @Transactional
    public HashMap<String, String> updateCompanyDetails(String accessToken, Company newCompany) throws com.CrimsonBackendDatabase.crimsondb.CompanyMessages.CompanyTokenExceptions.InvalidTokenException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if(companyToken.isPresent()) {
            boolean isValid = companyTokenService.validateToken(accessToken, String.valueOf(companyToken.get().getCompany().getId()));
            if(isValid) {
                Company company = companyToken.get().getCompany();
                company = newCompany.clone();
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("result", "success");
                return data;
            } else {
                throw new com.CrimsonBackendDatabase.crimsondb.CompanyMessages.CompanyTokenExceptions.InvalidTokenException();
            }
        } else {
            throw new com.CrimsonBackendDatabase.crimsondb.CompanyMessages.CompanyTokenExceptions.InvalidTokenException();
        }

    };
    public HashMap<String, String> changePassword(String accessToken, PasswordChange passwordChange) throws AuthenticationException, InvalidTokenException {
        Optional<CompanyToken> session = companyTokenService.findCompanyToken(accessToken);
        if(session.isPresent()) {
            boolean isValid = companyTokenService.validateToken(accessToken, String.valueOf(session.get().getCompany().getId()));
            if(isValid) {
                Company company = session.get().getCompany();
                boolean val =  encoder.matches(
                        passwordChange.getOldPassword(),
                        company.getPassword()
                );
                if (val) {
                    company.setPassword(encoder.encode(passwordChange.getNewPassword()));
                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put("result", "success");
                    return data;
                }else {
                    throw new AuthenticationException("Incorrect old password");
                }

            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }
    }

    @Transactional
    public HashMap<String, String> validateEmail(String accessToken) throws InvalidTokenException {
        Optional<CompanyToken> session = companyTokenService.findCompanyToken(accessToken);
        if(session.isPresent()) {
            boolean isValid = companyTokenService.validateToken(accessToken, String.valueOf(session.get().getCompany().getId()));
            if(isValid) {
                Company company = session.get().getCompany();
                company.setEmailValid(true);
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("result", "success");
                return data;
            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }
    };

    public HashMap<String, String> validatePrimaryPhoneNumber(String accessToken) throws InvalidTokenException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if(companyToken.isPresent()) {
            boolean isValid = companyTokenService.validateToken(accessToken, String.valueOf(companyToken.get().getCompany().getId()));
            if(isValid) {
                Company company = companyToken.get().getCompany();
                company.setPhoneNumberValid(true);
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("result", "success");
                return data;
            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }
    };
}
