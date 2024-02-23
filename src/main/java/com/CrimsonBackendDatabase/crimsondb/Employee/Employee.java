package com.CrimsonBackendDatabase.crimsondb.Employee;

import com.CrimsonBackendDatabase.crimsondb.Applications.Applications;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Date dateOfBirth;
    private String gender;
    private String address;
    private String position;
    private String department;
    private Date startDate;
    private String criminalRecord;
    private String medicalReport;
    private String nationalIdFront;
    private String nationalIdBack;
    private String passport;
    private String nextOfKinName;
    private String nextOfKinRelationship;
    private String nextOfKinPhone;
    private String nextOfKinAddress;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private Users user;
}
