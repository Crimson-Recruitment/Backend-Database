package com.CrimsonBackendDatabase.crimsondb.Meetings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/meeting")
public class MeetingsController {
    private final MeetingsService meetingsService;
    @Autowired
    public MeetingsController(MeetingsService meetingsService) {
        this.meetingsService = meetingsService;
    }
    @PostMapping("/schedule-meeting/{user_id}")
    public HashMap<String, String> scheduleMeeting(){
        return meetingsService.scheduleMeeting();
    }

    @GetMapping("/meeting-info/{meeting_id}")
    public ResponseEntity<Meetings> getMeetingInfo() {
        return new ResponseEntity<Meetings>(meetingsService.getMeetingDetails(), HttpStatus.OK);
    }
}
