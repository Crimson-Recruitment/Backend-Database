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
    private int duration;
    private boolean pre_schedule;
    private List<HashMap<String,String>> meeting_invitees;
    @DateTimeFormat(pattern = "YYYY-mm-dd'T'HH:mm:ss'Z'")
    private Date start_time;
    private String timezone;
    private String topic;

    public MeetingInfo() {
    }

    public MeetingInfo(String agenda, boolean defaultPassword, int duration,String password, boolean pre_schedule, List<HashMap<String, String>> meeting_invitees, Date start_time, String timezone, String topic) {
        this.agenda = agenda;
        this.defaultPassword = defaultPassword;
        this.password = password;
        this.pre_schedule = pre_schedule;
        this.meeting_invitees = meeting_invitees;
        this.start_time = start_time;
        this.duration = duration;
        this.timezone = timezone;
        this.topic = topic;
    }

    public String getAgenda() {
        return agenda;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public boolean isDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(boolean defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPre_schedule() {
        return pre_schedule;
    }

    public void setPre_schedule(boolean pre_schedule) {
        this.pre_schedule = pre_schedule;
    }

    public List<HashMap<String, String>> getMeeting_invitees() {
        return meeting_invitees;
    }

    public void setMeeting_invitees(List<HashMap<String, String>> meeting_invitees) {
        this.meeting_invitees = meeting_invitees;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
