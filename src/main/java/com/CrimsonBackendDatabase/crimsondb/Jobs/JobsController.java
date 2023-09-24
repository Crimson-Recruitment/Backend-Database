package com.CrimsonBackendDatabase.crimsondb.Jobs;

import com.CrimsonBackendDatabase.crimsondb.Company.CompanyExceptions.InvalidCompanyException;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("v1/jobs")
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
    public List<Jobs> getAllCompanyJobs(@PathVariable("id") Long id) {
        try {
            return jobsService.getAllCompanyJobs(id);
        } catch (InvalidTokenException | InvalidCompanyException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/company-jobs/field/{field}")
    public List<Jobs> getJobsByField(@PathVariable("field")String field) {
        try {
            return jobsService.getJobsByField(field);
        } catch (InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }
}
