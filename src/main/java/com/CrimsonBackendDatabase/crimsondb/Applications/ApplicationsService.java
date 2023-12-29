package com.CrimsonBackendDatabase.crimsondb.Applications;

import com.CrimsonBackendDatabase.crimsondb.Applications.ApplicationsException.CreateApplicationsException;
import com.CrimsonBackendDatabase.crimsondb.Applications.ApplicationsException.InvalidApplicationException;
import com.CrimsonBackendDatabase.crimsondb.Applications.Models.ApplicationModel;
import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyToken;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenService;
import com.CrimsonBackendDatabase.crimsondb.Jobs.JobExceptions.AccessDeniedException;
import com.CrimsonBackendDatabase.crimsondb.Jobs.Jobs;
import com.CrimsonBackendDatabase.crimsondb.Jobs.JobsRepository;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserToken;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ApplicationsService {
    private final UserTokenService userTokenService;
    private final CompanyTokenService companyTokenService;
    private final JobsRepository jobsRepository;
    private final ApplicationsRepository applicationsRepository;
    @Autowired
    public ApplicationsService(UserTokenService userTokenService, JobsRepository jobsRepository, ApplicationsRepository applicationsRepository, CompanyTokenService companyTokenService) {
        this.userTokenService = userTokenService;
        this.jobsRepository = jobsRepository;
        this.applicationsRepository = applicationsRepository;
        this.companyTokenService = companyTokenService;
    }
    // User Functions
    @Transactional
    public HashMap<String, String> createApplication(String accessToken, ApplicationModel model, Long jobId) throws InvalidTokenException, CreateApplicationsException {
        Optional<UserToken> userToken = userTokenService.findUserToken(accessToken);
        if(userToken.isPresent()) {
            boolean isValid = userTokenService.validateToken(accessToken, String.valueOf(userToken.get().getUsers().getId()));
            if(isValid) {
                Optional<Jobs> job = jobsRepository.findById(jobId);
                if(job.isPresent()) {
                    Optional<Applications> findApplication = applicationsRepository.findApplicationByUserAndJob(userToken.get().getUsers(), job.get());
                    if(findApplication.isPresent()) {
                        throw new CreateApplicationsException("You have already applied to this job!");
                    } else {
                        Applications application = Applications.builder().timeStamp(model.getTimeStamp()).status("Submitted").coverLetter(model.getCoverLetter()).user(userToken.get().getUsers()).job(job.get()).build();
                        applicationsRepository.save(application);
                        HashMap<String, String> data = new HashMap<String, String>();
                        data.put("result", "success");
                        return data;
                    }
                } else {
                    throw new CreateApplicationsException("Job is either expired or invalid!");
                }
            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }
    };

    @Transactional
    public List<Applications> getAllUserApplications(String accessToken) throws InvalidTokenException {
        Optional<UserToken> userToken = userTokenService.findUserToken(accessToken);
        if(userToken.isPresent()) {
            boolean isValid = userTokenService.validateToken(accessToken, String.valueOf(userToken.get().getUsers().getId()));
            if(isValid) {
                HashMap<String, String> data = new HashMap<String, String>();
                Optional<List<Applications>> applications = applicationsRepository.findApplicationsByUsers(userToken.get().getUsers());
                return applications.orElseGet(ArrayList::new);
            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }
    };
    // Company Functions
    @Transactional
    public HashMap<String, String> updateApplicationStatus(Long id, String status,String accessToken) throws InvalidTokenException, InvalidApplicationException, AccessDeniedException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if(companyToken.isPresent()) {
            boolean isValid = companyTokenService.validateToken(accessToken, String.valueOf(companyToken.get().getCompany().getId()));
            if(isValid) {
                Optional<Applications> application = applicationsRepository.findById(id);
                if(application.isPresent()) {
                    Company company = application.get().getJob().getCompany();
                    if(Objects.equals(company.getId(), companyToken.get().getCompany().getId())) {
                        application.get().setStatus(status);
                        System.out.println("Updated status Successfully");
                        HashMap<String, String> data = new HashMap<String, String>();
                        data.put("result", "success");
                        return data;
                    } else {
                        throw new AccessDeniedException();
                    }
                } else {
                    throw new InvalidApplicationException();
                }
            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }
    };

    @Transactional
    public List<Applications> getAllCompanyApplications(String accessToken) throws InvalidTokenException {   Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if(companyToken.isPresent()) {
            boolean isValid = companyTokenService.validateToken(accessToken, String.valueOf(companyToken.get().getCompany().getId()));
            if(isValid) {
                Optional<List<Jobs>> jobs = jobsRepository.findJobsByCompany(companyToken.get().getCompany());
                if(jobs.isPresent()) {
                    List<Applications> applications = new ArrayList<Applications>();
                    for(Jobs job: jobs.get()) {
                            applications.addAll(job.getApplications());
                    }
                    return applications;
                } else {
                    return new ArrayList<>();
                }
            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw  new InvalidTokenException();
        }

    }



}
