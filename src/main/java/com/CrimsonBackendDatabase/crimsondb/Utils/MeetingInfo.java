package com.CrimsonBackendDatabase.crimsondb.Utils;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingInfo {
    private String agenda;
    private boolean default_password = false;
    private String password;
    private int duration;
    private boolean pre_schedule = false;
    private String start_time;
    private Integer type = 2;
    private String timezone;
    private String topic;
    private String location;
    private String streetName;
    private String otherDetails;
    private String contactPhoneNumber;
    private String contactEmail;
    private String meetingType;
}
