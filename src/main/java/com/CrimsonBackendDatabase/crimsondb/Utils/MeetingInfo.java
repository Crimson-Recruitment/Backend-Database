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
    private String start_time;
    private String timezone;
    private String topic;

    public MeetingInfo() {
    }

    public MeetingInfo(String agenda, boolean defaultPassword, int duration,String password, boolean pre_schedule, String start_time, String timezone, String topic) {
        this.agenda = agenda;
        this.defaultPassword = defaultPassword;
        this.password = password;
        this.pre_schedule = pre_schedule;
        this.start_time = start_time;
        this.duration = duration;
        this.timezone = timezone;
        this.topic = topic;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

    public void setPre_schedule(boolean pre_schedule) {
        this.pre_schedule = pre_schedule;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
