package com.CrimsonBackendDatabase.crimsondb.Meetings;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.Jobs.Jobs;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Entity
@Table
public class Meetings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String joinUrl;
    private String agenda;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startTime;
    private String timeZone;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="job_id",referencedColumnName = "id")
    private Jobs job;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private Users user;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    public Meetings() {
    }

    public Meetings(String joinUrl, String agenda, Date startTime, String timeZone, Users user, Company company) {
        this.joinUrl = joinUrl;
        this.agenda = agenda;
        this.startTime = startTime;
        this.timeZone = timeZone;
        this.user = user;
        this.company = company;
    }


    public void setJoinUrl(String joinUrl) {
        this.joinUrl = joinUrl;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
