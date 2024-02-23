package com.CrimsonBackendDatabase.crimsondb.Users;

import com.CrimsonBackendDatabase.crimsondb.Applications.Applications;
import com.CrimsonBackendDatabase.crimsondb.UserMessageManager.UserMessageManager;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserToken;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.apache.maven.surefire.shared.lang3.RandomStringUtils;

import java.util.Collection;
import java.util.List;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private boolean phoneNumberValid;
    private String email;
    private String location;
    private boolean emailValid;
    private boolean enableNotifications;
    private String hash;

    @Column(length = 2048)
    private String profileImage;

    @Column(length = 2048)
    private String cv;
    private String password;
    private String jobTitle;
    @Column(length = 2048)
    private String bio;
    private List<String> skills;
    private String tier;
    private String paymentRandom;

    @JsonIgnore
    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
    private UserToken userToken;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Collection<Applications> applications;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private Users user;
    public Users() {
        paymentRandom = RandomStringUtils.randomAlphanumeric(8);
    }

    public Users(String firstName, String lastName, String phoneNumber, String email, String profileImage, String cv, String jobTitle, String bio, List<String> skills, String tier) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.phoneNumberValid = false;
        this.email = email;
        this.skills = skills;
        this.emailValid = false;
        this.profileImage = profileImage;
        this.cv = cv;
        this.jobTitle = jobTitle;
        this.bio = bio;
        this.tier = tier;
        paymentRandom = RandomStringUtils.randomAlphanumeric(8);
    }


    public String getPaymentRandom() {
        return paymentRandom;
    }

    public void setPaymentRandom(String paymentRandom) {
        this.paymentRandom = paymentRandom;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isPhoneNumberValid() {
        return phoneNumberValid;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public boolean isEmailValid() {
        return emailValid;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getCv() {
        return cv;
    }

    public String getPassword() {
        return password;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getBio() {
        return bio;
    }

    public List<String> getSkills() {
        return skills;
    }

    public String getTier() {
        return tier;
    }

    public UserToken getUserToken() {
        return userToken;
    }

    public Collection<Applications> getApplications() {
        return applications;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setPhoneNumberValid(boolean phoneNumberValid) {
        this.phoneNumberValid = phoneNumberValid;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setEmailValid(boolean emailValid) {
        this.emailValid = emailValid;
    }
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    public void setCv(String cv) {
        this.cv = cv;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

}
