package com.CrimsonBackendDatabase.crimsondb.Utils;

import lombok.Getter;

import java.util.List;

@Getter
public class CompanyDetails {
    private Long companyId;
    private String companyName;
    private String field;
    private String profileImage;
    private List<String> companyImages;
    private String overview;
    private String email;
    private String primaryPhoneNumber;
    private String secondaryPhoneNumber;

    public CompanyDetails(Long companyId,String companyName, String field, String email, String profileImage, List<String> companyImages, String overview, String primaryPhoneNumber, String secondaryPhoneNumber) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.field = field;
        this.profileImage = profileImage;
        this.companyImages = companyImages;
        this.overview = overview;
        this.email = email;
        this.primaryPhoneNumber = primaryPhoneNumber;
        this.secondaryPhoneNumber = secondaryPhoneNumber;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setCompanyImages(List<String> companyImages) {
        this.companyImages = companyImages;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber = primaryPhoneNumber;
    }

    public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
    }
}
