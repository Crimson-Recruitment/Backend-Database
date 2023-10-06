package com.CrimsonBackendDatabase.crimsondb.Meetings;

import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.Utils.MeetingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/meeting")
public class MeetingsController {
    private final MeetingsService meetingsService;
    @Autowired
    public MeetingsController(MeetingsService meetingsService) {
        this.meetingsService = meetingsService;
    }
    @PostMapping("/schedule-meeting/{userId}")
    public HashMap<String, String> scheduleMeeting(@RequestHeader("Authorization") String accessToken,@RequestBody MeetingInfo meetingInfo){
        try {
            return meetingsService.scheduleMeeting(accessToken,meetingInfo);
        } catch (InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/meeting-info/{meetingId}")
    public ResponseEntity<Meetings> getMeetingInfo() {
        return new ResponseEntity<Meetings>(meetingsService.getMeetingDetails(), HttpStatus.OK);
    }

    @GetMapping("/get-code")
    public HashMap<String, String> getCode(@RequestParam("accessCode") String accessToken,@RequestParam("code") String code){
        try {
            return meetingsService.getCode(accessToken,code);
        } catch (InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }
}
