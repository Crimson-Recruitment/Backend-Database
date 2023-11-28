package com.CrimsonBackendDatabase.crimsondb.Applications;

import com.CrimsonBackendDatabase.crimsondb.Applications.ApplicationsException.CreateApplicationsException;
import com.CrimsonBackendDatabase.crimsondb.Applications.ApplicationsException.InvalidApplicationException;
import com.CrimsonBackendDatabase.crimsondb.Applications.Models.ApplicationModel;
import com.CrimsonBackendDatabase.crimsondb.Jobs.JobExceptions.AccessDeniedException;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationsController {
    private ApplicationsService applicationsService;
    @Autowired
    public ApplicationsController(ApplicationsService applicationsService) {
        this.applicationsService = applicationsService;
    }

    @PostMapping("/create-application/{jobId}")
    public HashMap<String, String> createApplication(@RequestBody ApplicationModel model, @RequestHeader("Authorization") String accessToken, @PathVariable("jobId") Long jobId) {
        try {
            return  applicationsService.createApplication(accessToken,model,jobId);
        } catch (InvalidTokenException | CreateApplicationsException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/user-applications")
    public List<Applications> getUserApplications(@RequestHeader("Authorization") String accessToken) {
        try {
            return applicationsService.getAllUserApplications(accessToken);
        } catch (InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/update-application/{id}")
    public HashMap<String, String> updateApplicationStatus(@PathVariable("id") Long id, @RequestBody ApplicationModel model, @RequestHeader("Authorization") String accessToken) {
        try {
            return applicationsService.updateApplicationStatus(id, model.getStatus(),accessToken);
        } catch (InvalidTokenException | InvalidApplicationException | AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/company-applications")
    public List<Applications> getAllCompanyApplications(@RequestHeader("Authorization") String accessToken) {
        try {
            return applicationsService.getAllCompanyApplications(accessToken);
        } catch (InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }

}
