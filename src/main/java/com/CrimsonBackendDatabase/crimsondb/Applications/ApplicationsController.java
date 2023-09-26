package com.CrimsonBackendDatabase.crimsondb.Applications;

import com.CrimsonBackendDatabase.crimsondb.Applications.ApplicationsException.CreateApplicationsException;
import com.CrimsonBackendDatabase.crimsondb.Applications.ApplicationsException.InvalidApplicationException;
import com.CrimsonBackendDatabase.crimsondb.Jobs.JobExceptions.AccessDeniedException;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.Utils.Status;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationsController {
    private ApplicationsService applicationsService;
    @PostMapping("/create-application/{id}")
    public HashMap<String, String> createApplication(@RequestHeader("Authorization") String accessToken, @PathVariable("id") Long id) {
        try {
            return  applicationsService.createApplication(accessToken,id);
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

    @GetMapping("/update-application/{id}")
    public HashMap<String, String> updateApplicationStatus(@PathVariable("id") Long id, @RequestBody Status status, @RequestHeader("Authorization") String accessToken) {
        try {
            return applicationsService.updateApplicationStatus(id, status.getStatus(),accessToken);
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
