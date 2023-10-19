package com.CrimsonBackendDatabase.crimsondb.Utils;

import lombok.Getter;

@Getter
public class UserDetails {
    private String firstName;
    private String lastName;
    private String profileImage;
    private String phoneNumber;
    private String email;
    private String location;
    private String cv;

    public UserDetails(String firstName, String lastName, String profileImage, String phoneNumber, String email, String location, String cv) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImage = profileImage;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.location = location;
        this.cv = cv;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }
}
