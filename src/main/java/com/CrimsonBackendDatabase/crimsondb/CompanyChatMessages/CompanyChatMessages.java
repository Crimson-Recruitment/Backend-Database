package com.CrimsonBackendDatabase.crimsondb.CompanyChatMessages;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.CompanyMessageManager.CompanyMessageManager;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table
@NoArgsConstructor
public class CompanyChatMessages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Long receiverId;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "msgmanager_id", referencedColumnName = "id")
    private CompanyMessageManager companyMessageManager;

    public CompanyChatMessages(String message,Long receiverId, CompanyMessageManager companyMessageManager) {
        this.message = message;
        this.receiverId = receiverId;
        this.companyMessageManager = companyMessageManager;
    }
}
