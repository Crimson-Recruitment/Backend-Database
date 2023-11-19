package com.CrimsonBackendDatabase.crimsondb.Jobs;

import com.CrimsonBackendDatabase.crimsondb.Company.CompanyExceptions.InvalidCompanyException;
import com.CrimsonBackendDatabase.crimsondb.Jobs.JobExceptions.AccessDeniedException;
import com.CrimsonBackendDatabase.crimsondb.Jobs.JobExceptions.InvalidJobException;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
@CrossOrigin(origins = "*")
public class JobsController {
    private final JobsService jobsService;
    @Autowired
    public JobsController(JobsService jobsService) {
        this.jobsService = jobsService;
    }
    @PostMapping("/post-job")
    public HashMap<String, String> postJob(@RequestBody Jobs job, @RequestHeader("Authorization") String accessToken) {
        try {
            return jobsService.postJob(job, accessToken);
        } catch (InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/company-jobs/{id}")
    public List<Jobs> getAllCompanyJobs(@PathVariable("id") Long id,@RequestHeader("Authorization") String accessToken) {
        try {
            return jobsService.getAllCompanyJobs(id,accessToken);
        } catch (InvalidTokenException | InvalidCompanyException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/company-jobs/field/{field}")
    public List<Jobs> getJobsByField(@PathVariable("field")String field, @RequestHeader("Authorization") String accessToken) {
        try {
            return jobsService.getJobsByField(field,accessToken);
        } catch (InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/all")
    public List<Jobs> getAllJobs(@RequestHeader("Authorization") String accessToken) {
        try {
            return jobsService.getAllJobs(accessToken);
        } catch (InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/update-job/{jobId}")
    public  HashMap<String, String> updateJob(@RequestHeader("Authorization") String accessToken, @RequestBody Jobs job, @PathVariable("jobId") Long jobId) {
        try {
            return jobsService.updateJob(jobId,job,accessToken);
        } catch (InvalidTokenException | AccessDeniedException | InvalidJobException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/delete-job/{jobId}")
    public  HashMap<String, String> deleteJob(@RequestHeader("Authorization") String accessToken, @PathVariable("jobId") Long jobId) {
        try {
            return jobsService.deleteJob(jobId,accessToken);
        } catch (InvalidTokenException | AccessDeniedException | InvalidJobException e) {
            throw new RuntimeException(e);
        }
    }
}
