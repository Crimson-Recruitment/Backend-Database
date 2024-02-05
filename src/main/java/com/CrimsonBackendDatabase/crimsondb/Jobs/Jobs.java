package com.CrimsonBackendDatabase.crimsondb.Jobs;

import com.CrimsonBackendDatabase.crimsondb.Applications.Applications;
import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.Meetings.Meetings;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jobs implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobTitle;
    private String jobType;
    private String companyName;
    private String companyOverview;
    private String locationType;
    private String field;
    private String otherSite;
    @Column(length = 2048)
    private String jobDescription;
    private String location;
    private List<String> requirements;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date expiryDate;
    private List<String> skills;
    private Integer minSalary;
    private Integer maxSalary;
    private boolean hideSalary;
    private List<String> benefits;
    private boolean requestCoverLetter;
    @Column(length = 2048)
    private String otherDetails;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date timestamp;

    @ManyToOne( cascade = CascadeType.DETACH)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @JsonIgnore
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private Collection<Applications> applications;

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
