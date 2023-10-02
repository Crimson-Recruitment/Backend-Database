package com.CrimsonBackendDatabase.crimsondb.Meetings;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table
public class Meetings {
    private String joinUrl;
    private String agenda;
    private Date startTime;
    private String timeZone;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private Users user;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

}
