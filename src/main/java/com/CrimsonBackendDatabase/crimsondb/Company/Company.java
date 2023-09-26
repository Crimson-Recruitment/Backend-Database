package com.CrimsonBackendDatabase.crimsondb.Company;

import com.CrimsonBackendDatabase.crimsondb.CompanyImages.CompanyImages;
import com.CrimsonBackendDatabase.crimsondb.CompanyMessages.CompanyMessages;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyToken;
import com.CrimsonBackendDatabase.crimsondb.Jobs.Jobs;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Entity
@Table
public class Company implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String email;
    private boolean emailValid;
    @Lob
    private byte[] profileImage;
    private String primaryPhoneNumber;
    private boolean phoneNumberValid;
    private String secondaryPhoneNumber;
    private String category;
    private String tier;
    private String overview;
    @JsonIgnore
    private String password;
    private String zoomCode;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<CompanyImages> companyImages;
    @OneToOne(mappedBy = "company",cascade = CascadeType.ALL)
    private CompanyToken companyToken;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Collection<Jobs> jobs;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Collection<CompanyMessages> companyMessages;

    public Company(String companyName,String email,String overview, byte[] profileImage, String primaryPhoneNumber, boolean phoneNumberValid, String secondaryPhoneNumber, String category, String tier,String password) {
        this.companyName = companyName;
        this.email = email;
        this.profileImage = profileImage;
        this.primaryPhoneNumber = primaryPhoneNumber;
        this.phoneNumberValid = phoneNumberValid;
        this.secondaryPhoneNumber = secondaryPhoneNumber;
        this.category = category;
        this.overview = overview;
        this.tier = tier;
        this.password = password;
    }

    public String getOverview() {
        return overview;
    }

    public String getZoomCode() {
        return zoomCode;
    }

    public void setZoomCode(String zoomCode) {
        this.zoomCode = zoomCode;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEmailValid() {
        return emailValid;
    }

    public void setEmailValid(boolean emailValid) {
        this.emailValid = emailValid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Collection<CompanyMessages> getCompanyMessages() {
        return companyMessages;
    }

    public Collection<CompanyImages> getCompanyImages() {
        return companyImages;
    }

    public void setCompanyImages(Collection<CompanyImages> companyImages) {
        this.companyImages = companyImages;
    }

    public void setCompanyMessages(Collection<CompanyMessages> companyMessages) {
        this.companyMessages = companyMessages;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public String getPrimaryPhoneNumber() {
        return primaryPhoneNumber;
    }

    public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber = primaryPhoneNumber;
    }

    public boolean isPhoneNumberValid() {
        return phoneNumberValid;
    }

    public void setPhoneNumberValid(boolean phoneNumberValid) {
        this.phoneNumberValid = phoneNumberValid;
    }

    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }

    public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    @Override
    public Company clone() {
        try {
            Company clone = (Company) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
