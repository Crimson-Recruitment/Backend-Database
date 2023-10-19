package com.CrimsonBackendDatabase.crimsondb.Meetings;

import com.CrimsonBackendDatabase.crimsondb.Applications.Applications;
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
    private String password;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startTime;
    private String timeZone;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="application_id", referencedColumnName = "id")
    private Applications application;

    public Meetings() {
    }

    public Meetings(String joinUrl, String agenda, Date startTime, String password,String timeZone, Applications application) {
        this.joinUrl = joinUrl;
        this.agenda = agenda;
        this.startTime = startTime;
        this.password = password;
        this.timeZone = timeZone;
        this.application = application;
    }

    public void setPassword(String password) {
        this.password = password;
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

}
