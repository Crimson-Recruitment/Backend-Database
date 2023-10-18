package com.CrimsonBackendDatabase.crimsondb.Jobs;

import com.CrimsonBackendDatabase.crimsondb.Applications.Applications;
import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.Meetings.Meetings;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
public class Jobs implements Cloneable {
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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date expiryDate;
    private Integer minSalary;
    private Integer maxSalary;
    private boolean hideSalary;
    private List<String> benefits;
    private boolean requestCoverLetter;
    private String otherDetails;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private Collection<Applications> applications;
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private Collection<Meetings> meetings;


    public Jobs() {
    }

    public Jobs(String jobTitle, String jobType, boolean isVolunteering, String locationType, String field, String jobDescription, String location, List<String> requirements, Date expiryDate, Integer minSalary, Integer maxSalary, boolean hideSalary, List<String> benefits, boolean requestCoverLetter, String coverLetter, String otherDetails) {
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

    public Long getId() {
        return id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getJobType() {
        return jobType;
    }

    public String getLocationType() {
        return locationType;
    }

    public String getField() {
        return field;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getRequirements() {
        return requirements;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public Integer getMinSalary() {
        return minSalary;
    }

    public Integer getMaxSalary() {
        return maxSalary;
    }

    public boolean isHideSalary() {
        return hideSalary;
    }

    public List<String> getBenefits() {
        return benefits;
    }

    public boolean isRequestCoverLetter() {
        return requestCoverLetter;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public Company getCompany() {
        return company;
    }

    public Collection<Applications> getApplications() {
        return applications;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
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

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRequirements(List<String> requirements) {
        this.requirements = requirements;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setMinSalary(Integer minSalary) {
        this.minSalary = minSalary;
    }

    public void setMaxSalary(Integer maxSalary) {
        this.maxSalary = maxSalary;
    }

    public void setHideSalary(boolean hideSalary) {
        this.hideSalary = hideSalary;
    }

    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }

    public void setRequestCoverLetter(boolean requestCoverLetter) {
        this.requestCoverLetter = requestCoverLetter;
    }
    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public Jobs clone() {
        try {
            Jobs clone = (Jobs) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
