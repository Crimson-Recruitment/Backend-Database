package com.CrimsonBackendDatabase.crimsondb.CompanyImages;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import jakarta.persistence.*;

@Entity
@Table
public class CompanyImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] image;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;
}
