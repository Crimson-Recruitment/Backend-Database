package com.CrimsonBackendDatabase.crimsondb.Company;

import com.CrimsonBackendDatabase.crimsondb.CompanyMessages.CompanyMessages;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyToken;
import com.CrimsonBackendDatabase.crimsondb.Jobs.Jobs;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import org.apache.maven.surefire.shared.lang3.RandomStringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Random;

@Getter
@Entity
@Table
public class Company{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String email;
    private boolean emailValid;
    @Column(length = 2048)
    private String profileImage;
    private String primaryPhoneNumber;
    private boolean phoneNumberValid;
    private String secondaryPhoneNumber;
    private String category;
    private String tier;
    private String overview;
    private String password;
    @Column(length = 2048)
    private String zoomAccessToken;
    @Column(length = 2048)
    private String zoomRefreshToken;
    private String paymentRandom = RandomStringUtils.randomAlphanumeric(8);

    private List<String> companyImages;

    @JsonIgnore
    @OneToOne(mappedBy = "company",cascade = CascadeType.ALL)
    private CompanyToken companyToken;

    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Collection<Jobs> jobs;

    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Collection<CompanyMessages> companyMessages;

    public Company() {
        paymentRandom = RandomStringUtils.randomAlphanumeric(8);
    }

    public Company(String companyName, String email, String overview, String profileImage,List<String> companyImages,String primaryPhoneNumber, String secondaryPhoneNumber, String category, String tier, String password) {
        this.companyName = companyName;
        this.email = email;
        this.profileImage = profileImage;
        this.primaryPhoneNumber = primaryPhoneNumber;
        this.phoneNumberValid = false;
        this.emailValid = false;
        this.secondaryPhoneNumber = secondaryPhoneNumber;
        this.category = category;
        this.companyImages = companyImages;
        this.overview = overview;
        this.tier = tier;
        this.password = password;
        paymentRandom = RandomStringUtils.randomAlphanumeric(8);
    }

    public void setPaymentRandom(String paymentRandom) {
        paymentRandom = paymentRandom;
    }

    public void setZoomRefreshToken(String zoomRefreshToken) {
        this.zoomRefreshToken = zoomRefreshToken;
    }

    public void setZoomAccessToken(String zoomAccessToken) {
        this.zoomAccessToken = zoomAccessToken;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setEmailValid(boolean emailValid) {
        this.emailValid = emailValid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyMessages(Collection<CompanyMessages> companyMessages) {
        this.companyMessages = companyMessages;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCompanyImages(List<String> companyImages) {
        this.companyImages = companyImages;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber = primaryPhoneNumber;
    }

    public void setPhoneNumberValid(boolean phoneNumberValid) {
        this.phoneNumberValid = phoneNumberValid;
    }

    public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

}
