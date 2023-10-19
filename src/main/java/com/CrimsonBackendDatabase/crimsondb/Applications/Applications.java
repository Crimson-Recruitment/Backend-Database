package com.CrimsonBackendDatabase.crimsondb.Applications;

import com.CrimsonBackendDatabase.crimsondb.Jobs.Jobs;
import com.CrimsonBackendDatabase.crimsondb.Meetings.Meetings;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Collection;

@Getter
@Entity
@Table
public class Applications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    private Jobs job;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @OneToOne(mappedBy = "application", cascade = CascadeType.ALL)
    private Meetings meetings;

    public Applications(String status, Jobs job, Users user) {
        this.status = status;
        this.job = job;
        this.user = user;
    }

    public Applications() {
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setJob(Jobs job) {
        this.job = job;
    }

    public void setUser(Users user) {
        this.user = user;
    }

}
