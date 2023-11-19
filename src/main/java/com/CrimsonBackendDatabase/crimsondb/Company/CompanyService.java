package com.CrimsonBackendDatabase.crimsondb.Company;

import com.CrimsonBackendDatabase.crimsondb.Company.CompanyExceptions.InvalidCompanyException;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyToken;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenService;
import com.CrimsonBackendDatabase.crimsondb.Exceptions.AuthenticationException;
import com.CrimsonBackendDatabase.crimsondb.Exceptions.EmailAlreadyExistsException;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.Users.UsersException.InvalidUserException;
import com.CrimsonBackendDatabase.crimsondb.Utils.CompanyDetails;
import com.CrimsonBackendDatabase.crimsondb.Utils.PasswordChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
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
            company.setTier("Free");
            companyRepository.save(company);
            String accessToken = companyTokenService.generateToken(company);
            HashMap<String, String> result = new HashMap<String, String>();
            result.put("result", "success");
            result.put("accessToken",accessToken);
            return result;
        }
    };

    public HashMap<String, String> companyLogin(String email, String password) throws com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenExceptions.InvalidTokenException, AuthenticationException {
        Optional<Company> user = companyRepository.findCompanyByEmail(email);
        if (user.isPresent()) {
            boolean val =  encoder.matches(password,user.get().getPassword());
            if(val){
                String accessToken = companyTokenService.regenerateToken(user.get());
                HashMap<String, String> result = new HashMap<String, String>();
                result.put("result","success");
                result.put("accessToken",accessToken);
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
            return new CompanyDetails(company.get().getId(),company.get().getCompanyName(),company.get().getCategory(),company.get().getEmail(),company.get().getProfileImage(),new ArrayList<String>(company.get().getCompanyImages()),company.get().getOverview(),company.get().getPrimaryPhoneNumber(),company.get().getSecondaryPhoneNumber());
        } else {
            throw new InvalidCompanyException();
        }
    }


    public Company getCompanyDetails(String accessToken) throws InvalidTokenException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if(companyToken.isPresent()) {
            boolean isValid = companyTokenService.validateToken(accessToken, String.valueOf(companyToken.get().getCompany().getId()));
            if(isValid) {
                return companyToken.get().getCompany();
            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }
    }

    @Transactional
    public HashMap<String, String> updateCompanyDetails(String accessToken, Company newCompany) throws InvalidTokenException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if(companyToken.isPresent()) {
            boolean isValid = companyTokenService.validateToken(accessToken, String.valueOf(companyToken.get().getCompany().getId()));
            if(isValid) {
                companyRepository.findById(companyToken.get().getCompany().getId()).map(target -> {
                  target.setCompanyName(newCompany.getCompanyName());
                  if(!Objects.equals(newCompany.getEmail(),target.getEmail())) {
                      target.setEmail(newCompany.getEmail());
                      target.setEmailValid(false);
                    };
                  target.setProfileImage(newCompany.getProfileImage());
                  if(!Objects.equals(target.getPrimaryPhoneNumber(),newCompany.getPrimaryPhoneNumber())) {
                      target.setPrimaryPhoneNumber(newCompany.getPrimaryPhoneNumber());
                      target.setPhoneNumberValid(false);
                  };
                  target.setSecondaryPhoneNumber(newCompany.getSecondaryPhoneNumber());
                  target.setOverview(newCompany.getOverview());
                  return target;
                });
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

    public HashMap<String, String> getAccessToken(String email) throws InvalidUserException {
        Optional<Company> company = companyRepository.findCompanyByEmail(email);
        if(company.isPresent()) {
            Optional<HashMap<String, String>> val = company.map(target -> {
                HashMap<String,String> result = new HashMap<String, String>();
                result.put("result","success");
                result.put("accessToken",target.getCompanyToken().getAccessToken());
                return result;
            });
            return val.orElseThrow();
        } else {
            throw new InvalidUserException();
        }
    }
    @Transactional
    public HashMap<String, String> changePassword(String accessToken, PasswordChange passwordChange) throws AuthenticationException, InvalidTokenException {
        Optional<CompanyToken> session = companyTokenService.findCompanyToken(accessToken);
        if(session.isPresent()) {
            boolean isValid = companyTokenService.validateToken(accessToken, String.valueOf(session.get().getCompany().getId()));
            if(isValid) {
                Company company = session.get().getCompany();
                    company.setPassword(encoder.encode(passwordChange.getNewPassword()));
                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put("result", "success");
                    return data;
            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }
    }


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
