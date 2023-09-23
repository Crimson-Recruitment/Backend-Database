package com.CrimsonBackendDatabase.crimsondb.CompanyMessages;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table
public class CompanyMessages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private List<String> messageArray;
    private final String receiverType;
    private final Long receiverId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private final Company company;
    public CompanyMessages(List<String> messageArray, String receiverType, Long receiverId, Company company) {
        this.messageArray = messageArray;
        this.receiverType = receiverType;
        this.receiverId = receiverId;
        this.company = company;
    }


    public void setMessageArray(List<String> messageArray) {
        this.messageArray = messageArray;
    }

}
