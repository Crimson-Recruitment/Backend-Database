package com.CrimsonBackendDatabase.crimsondb.Utils;

import lombok.Getter;

import java.util.List;

@Getter
public class CompanyDetails {
    private String companyName;
    private String field;
    private byte[] profileImage;
    private List<byte[]> companyImages;
    private String overview;
    private String primaryPhoneNumber;
    private String secondaryPhoneNumber;

    public CompanyDetails(String companyName, String field, byte[] profileImage, List<byte[]> companyImages, String overview, String primaryPhoneNumber, String secondaryPhoneNumber) {
        this.companyName = companyName;
        this.field = field;
        this.profileImage = profileImage;
        this.companyImages = companyImages;
        this.overview = overview;
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

    public void setCompanyImages(List<byte[]> companyImages) {
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
