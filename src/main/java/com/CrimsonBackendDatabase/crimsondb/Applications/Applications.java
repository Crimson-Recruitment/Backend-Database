package com.CrimsonBackendDatabase.crimsondb.Applications;

import com.CrimsonBackendDatabase.crimsondb.Jobs.Jobs;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table
public class Applications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    private Jobs job;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    public Applications(String status, Jobs job, Users user) {
        this.status = status;
        this.job = job;
        this.user = user;
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
