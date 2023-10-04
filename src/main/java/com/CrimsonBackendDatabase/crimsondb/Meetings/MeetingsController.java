package com.CrimsonBackendDatabase.crimsondb.Meetings;

import com.CrimsonBackendDatabase.crimsondb.Utils.MeetingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/meeting")
public class MeetingsController {
    private final MeetingsService meetingsService;
    @Autowired
    public MeetingsController(MeetingsService meetingsService) {
        this.meetingsService = meetingsService;
    }
    @PostMapping("/schedule-meeting/{userId}")
    public HashMap<String, String> scheduleMeeting(@RequestBody MeetingInfo meetingInfo){
        return meetingsService.scheduleMeeting(meetingInfo);
    }

    @GetMapping("/meeting-info/{meetingId}")
    public ResponseEntity<Meetings> getMeetingInfo() {
        return new ResponseEntity<Meetings>(meetingsService.getMeetingDetails(), HttpStatus.OK);
    }
}
