package com.CrimsonBackendDatabase.crimsondb.Users;

import com.CrimsonBackendDatabase.crimsondb.Applications.Applications;
import com.CrimsonBackendDatabase.crimsondb.Jobs.Jobs;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserToken;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private boolean phoneNumberValid;
    private String email;
    private boolean emailValid;
    private String profileImage;
    private String cv;
    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
    private UserToken userToken;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Collection<Applications> applications;
    public Users() {
    }

    public Users(String firstName, String lastName, String phoneNumber, boolean phoneNumberValid, String email, boolean emailValid, String profileImage, String cv, String jobTitle, String bio, String tier) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.phoneNumberValid = phoneNumberValid;
        this.email = email;
        this.emailValid = emailValid;
        this.profileImage = profileImage;
        this.cv = cv;
        this.jobTitle = jobTitle;
        this.bio = bio;
        this.tier = tier;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isPhoneNumberValid() {
        return phoneNumberValid;
    }

    public void setPhoneNumberValid(boolean phoneNumberValid) {
        this.phoneNumberValid = phoneNumberValid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailValid() {
        return emailValid;
    }

    public void setEmailValid(boolean emailValid) {
        this.emailValid = emailValid;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    private String jobTitle;
    private String bio;
    private String tier;

}
