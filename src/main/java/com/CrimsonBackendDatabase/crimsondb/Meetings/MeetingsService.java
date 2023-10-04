package com.CrimsonBackendDatabase.crimsondb.Meetings;

import com.CrimsonBackendDatabase.crimsondb.Utils.MeetingInfo;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.HashMap;

@Service
public class MeetingsService {
    public HashMap<String, String> scheduleMeeting(MeetingInfo meetingInfo){
        System.out.println(meetingInfo.toString());
        HashMap<String,String> result = new HashMap<String, String>();
        result.put("result","success");
        return result;
    };

    public Meetings getMeetingDetails(){
        return null;
    };

}
