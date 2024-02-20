package com.CrimsonBackendDatabase.crimsondb.Meetings;

import com.CrimsonBackendDatabase.crimsondb.Applications.Applications;
import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.Jobs.Jobs;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Meetings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String joinUrl;
    private String location;
    private String streetName;
    private String otherDetails;
    private String contactPhoneNumber;
    private String contactEmail;
    private String meetingType;
    private Long userId;
    private Long companyId;
    private String agenda;
    private String password;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startTime;
    private String timeZone;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="application_id", referencedColumnName = "id")
    private Applications application;
}
