package com.CrimsonBackendDatabase.crimsondb.CompanyToken;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import jakarta.persistence.*;

@Entity
@Table
public class CompanyToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accessToken;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    public CompanyToken() {
    }

    public CompanyToken(String accessToken, Company company) {
        this.accessToken = accessToken;
        this.company = company;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
