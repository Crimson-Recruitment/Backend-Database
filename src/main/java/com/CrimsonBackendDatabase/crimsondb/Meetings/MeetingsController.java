package com.CrimsonBackendDatabase.crimsondb.Meetings;

import com.CrimsonBackendDatabase.crimsondb.Applications.ApplicationsException.InvalidApplicationException;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.Meetings.MeetingsException.InvalidMeetingException;
import com.CrimsonBackendDatabase.crimsondb.Meetings.MeetingsException.ZoomAuthorizationException;
import com.CrimsonBackendDatabase.crimsondb.Users.UsersException.InvalidUserException;
import com.CrimsonBackendDatabase.crimsondb.Utils.MeetingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/meeting")
public class MeetingsController {
    private final MeetingsService meetingsService;
    @Autowired
    public MeetingsController(MeetingsService meetingsService) {
        this.meetingsService = meetingsService;
    }
    @PostMapping("/schedule-meeting/{applicationId}")
    public HashMap<String, Object> scheduleMeeting(@RequestHeader("Authorization") String accessToken,@RequestBody MeetingInfo meetingInfo,@PathVariable("applicationId") Long applicationId){

        try {
            return meetingsService.scheduleMeeting(accessToken,meetingInfo, applicationId);
        } catch (InvalidTokenException | IOException | InterruptedException | ParseException |
                 InvalidApplicationException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/meeting-info/{meetingId}")
    public ResponseEntity<Meetings> getMeetingInfo(@RequestHeader("Authorization") String accessToken, @PathVariable("meetingId") Long id) {
        try {
            return new ResponseEntity<Meetings>(meetingsService.getMeetingDetails(accessToken, id), HttpStatus.OK);
        } catch (InvalidMeetingException | InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/refresh_token")
    public HashMap<String,String> refreshZoomAccessToken(@RequestHeader("Authorization") String accessToken) {
        try {
            return meetingsService.refreshAccessToken(accessToken);
        } catch (InvalidTokenException | IOException | InterruptedException | ZoomAuthorizationException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/get-code")
    public HashMap<String, String> getCode(@RequestParam("accessCode") String accessToken,@RequestParam("code") String code){

        try {
            return meetingsService.getCode(accessToken,code);
        } catch (InvalidTokenException | ExecutionException | InterruptedException | IOException |
                 ZoomAuthorizationException e) {
            throw new RuntimeException(e);
        }

    }
}
