package com.CrimsonBackendDatabase.crimsondb.Utils;

import com.CrimsonBackendDatabase.crimsondb.CompanyImages.CompanyImages;
import lombok.Getter;

import java.util.List;

@Getter
public class CompanyDetails {
    private String companyName;
    private String field;
    private byte[] profileImage;
    private List<CompanyImages> companyImages;
    private String overview;
    private String email;
    private String primaryPhoneNumber;
    private String secondaryPhoneNumber;

    public CompanyDetails(String companyName, String field, String email, byte[] profileImage, List<CompanyImages> companyImages, String overview, String primaryPhoneNumber, String secondaryPhoneNumber) {
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

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public void setCompanyImages(List<CompanyImages> companyImages) {
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
