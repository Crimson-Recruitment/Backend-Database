package com.CrimsonBackendDatabase.crimsondb.CompanyMessageManager;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.CompanyChatMessages.CompanyChatMessages;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Getter
@Entity
@Table
@NoArgsConstructor
public class CompanyMessageManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String receiverType;
    private Long receiverId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @JsonIgnore
    @OneToMany(mappedBy = "companyMessageManager", cascade = CascadeType.ALL)
    private Collection<CompanyChatMessages> companyChatMessages;

    public CompanyMessageManager(Long receiverId, String receiverType, Company company) {
        this.receiverId = receiverId;
        this.receiverType = receiverType;
        this.company = company;
    }

}
