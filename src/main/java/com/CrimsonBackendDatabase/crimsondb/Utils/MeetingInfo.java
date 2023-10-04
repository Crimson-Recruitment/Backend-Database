package com.CrimsonBackendDatabase.crimsondb.Utils;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Getter
public class MeetingInfo {
    private String agenda;
    private boolean defaultPassword;
    private String password;
    private boolean preschedule;
    private List<HashMap<String,String>> invitees;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    private String timezone;
    private String topic;

    public MeetingInfo() {
    }

    public MeetingInfo(String agenda, boolean defaultPassword, String password, boolean preschedule, List<HashMap<String, String>> invitees, Date startTime, String timezone, String topic) {
        this.agenda = agenda;
        this.defaultPassword = defaultPassword;
        this.password = password;
        this.preschedule = preschedule;
        this.invitees = invitees;
        this.startTime = startTime;
        this.timezone = timezone;
        this.topic = topic;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public void setDefaultPassword(boolean defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPreschedule(boolean preschedule) {
        this.preschedule = preschedule;
    }

    public void setInvitees(List<HashMap<String, String>> invitees) {
        this.invitees = invitees;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getType() {
        return 2;
    }

    @Override
    public String toString() {
        return "MeetingInfo{" +
                "agenda='" + agenda + '\'' +
                ", defaultPassword=" + defaultPassword +
                ", password='" + password + '\'' +
                ", preschedule=" + preschedule +
                ", invitees=" + invitees +
                ", startTime=" + startTime +
                ", timezone='" + timezone + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}
