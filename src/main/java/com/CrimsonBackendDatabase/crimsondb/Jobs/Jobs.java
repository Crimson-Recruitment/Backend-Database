package com.CrimsonBackendDatabase.crimsondb.Jobs;

import com.CrimsonBackendDatabase.crimsondb.Applications.Applications;
import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Jobs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTitle;
    private String jobType;
    private boolean isVolunteering;
    private String locationType;
    private String field;
    private String jobDescription;

    private String location;
    private List<String> requirements;
    private Date expiryDate;

    private Integer minSalary;
    private Integer maxSalary;
    private boolean hideSalary;
    private List<String> benefits;
    private boolean requestCoverLetter;
    private String otherDetails;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private Collection<Applications> applications;

    public Jobs() {
    }

    public Jobs(String jobTitle, String jobType, boolean isVolunteering, String locationType, String field, String jobDescription, String location, List<String> requirements, Date expiryDate, Integer minSalary, Integer maxSalary, boolean hideSalary, List<String> benefits, boolean requestCoverLetter) {
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.isVolunteering = isVolunteering;
        this.locationType = locationType;
        this.field = field;
        this.jobDescription = jobDescription;
        this.location = location;
        this.requirements = requirements;
        this.expiryDate = expiryDate;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.hideSalary = hideSalary;
        this.benefits = benefits;
        this.requestCoverLetter = requestCoverLetter;
    }

    public Jobs(String jobTitle, String jobType, boolean isVolunteering, String locationType, String field, String jobDescription, String location, List<String> requirements, Date expiryDate, Integer minSalary, Integer maxSalary, boolean hideSalary, List<String> benefits, boolean requestCoverLetter, String otherDetails) {
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.isVolunteering = isVolunteering;
        this.locationType = locationType;
        this.field = field;
        this.jobDescription = jobDescription;
        this.location = location;
        this.requirements = requirements;
        this.expiryDate = expiryDate;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.hideSalary = hideSalary;
        this.benefits = benefits;
        this.requestCoverLetter = requestCoverLetter;
        this.otherDetails = otherDetails;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public boolean isVolunteering() {
        return isVolunteering;
    }

    public void setVolunteering(boolean volunteering) {
        isVolunteering = volunteering;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<String> requirements) {
        this.requirements = requirements;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Integer minSalary) {
        this.minSalary = minSalary;
    }

    public Integer getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Integer maxSalary) {
        this.maxSalary = maxSalary;
    }

    public boolean isHideSalary() {
        return hideSalary;
    }

    public void setHideSalary(boolean hideSalary) {
        this.hideSalary = hideSalary;
    }

    public List<String> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }

    public boolean isRequestCoverLetter() {
        return requestCoverLetter;
    }

    public void setRequestCoverLetter(boolean requestCoverLetter) {
        this.requestCoverLetter = requestCoverLetter;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }
}
