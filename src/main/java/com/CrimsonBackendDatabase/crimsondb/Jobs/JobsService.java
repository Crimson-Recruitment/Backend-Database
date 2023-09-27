package com.CrimsonBackendDatabase.crimsondb.Jobs;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.Company.CompanyExceptions.InvalidCompanyException;
import com.CrimsonBackendDatabase.crimsondb.Company.CompanyRepository;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyToken;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenService;
import com.CrimsonBackendDatabase.crimsondb.Jobs.JobExceptions.AccessDeniedException;
import com.CrimsonBackendDatabase.crimsondb.Jobs.JobExceptions.InvalidJobException;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserToken;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobsService {
    private final CompanyTokenService companyTokenService;
    private final UserTokenService userTokenService;
    private final CompanyRepository companyRepository;
    private final JobsRepository jobsRepository;

    public JobsService(CompanyTokenService companyTokenService, JobsRepository jobsRepository, UserTokenService userTokenService, CompanyRepository companyRepository) {
        this.companyTokenService = companyTokenService;
        this.userTokenService = userTokenService;
        this.jobsRepository = jobsRepository;
        this.companyRepository = companyRepository;
    }

    public HashMap<String, String> postJob(Jobs job, String accessToken) throws InvalidTokenException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if (companyToken.isPresent()) {
            boolean isValid = companyTokenService.validateToken(accessToken, String.valueOf(companyToken.get().getCompany().getId()));
            if (isValid) {
                job.setCompany(companyToken.get().getCompany());
                jobsRepository.save(job);
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

    ;

    public List<Jobs> getAllCompanyJobs(Long id) throws InvalidTokenException, InvalidCompanyException {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            Optional<List<Jobs>> jobsByCompany = jobsRepository.findJobsByCompany(company.get());
            return jobsByCompany.orElseGet(ArrayList::new);
        } else {
            throw new InvalidCompanyException();
        }
    }

    ;

    public List<Jobs> getJobsByField(String field) throws InvalidTokenException {
        Optional<List<Jobs>> jobsByField = jobsRepository.findJobsByField(field);
        return jobsByField.orElseGet(ArrayList::new);
    }

    ;

    public List<Jobs> getAllJobs(String accessToken) throws InvalidTokenException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        Optional<UserToken> userToken = userTokenService.findUserToken(accessToken);
        if(userToken.isPresent() || companyToken.isPresent()) {
            return jobsRepository.findAll();
        } else {
            throw new  InvalidTokenException();
        }
    }

    ;

    public HashMap<String, String> updateJob(Long jobId, Jobs newJob,String accessToken) throws InvalidTokenException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if(companyToken.isPresent()) {
            boolean isValid = companyTokenService.validateToken(accessToken, String.valueOf(companyToken.get().getCompany().getId()));
            if(isValid) {
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
    public HashMap<String, String> deleteJob(Long jobId, String accessToken) throws InvalidTokenException, AccessDeniedException, InvalidJobException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if(companyToken.isPresent()) {
            boolean isValid = companyTokenService.validateToken(accessToken, String.valueOf(companyToken.get().getCompany().getId()));
            if(isValid) {
                Optional<Jobs> job = jobsRepository.findById(jobId);
                if(job.isPresent()) {
                    if(Objects.equals(companyToken.get().getCompany(), job.get().getCompany())){
                        jobsRepository.deleteById(jobId);
                        HashMap<String, String> data = new HashMap<String, String>();
                        data.put("result", "success");
                        return data;
                    } else {
                        throw new AccessDeniedException();
                    }
                } else {
                    throw new InvalidJobException();
                }
            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }
    };
}
