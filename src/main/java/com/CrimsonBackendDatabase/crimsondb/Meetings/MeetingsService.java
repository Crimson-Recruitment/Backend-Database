package com.CrimsonBackendDatabase.crimsondb.Meetings;

import com.CrimsonBackendDatabase.crimsondb.Utils.MeetingInfo;
import com.google.gson.Gson;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@Service
public class MeetingsService {
    @Async
    public HashMap<String, String> scheduleMeeting(MeetingInfo meetingInfo){
        System.out.println(meetingInfo.toString());
        HttpRequest request = HttpRequest
                .newBuilder(URI.create("https://api.zoom.us/v2/users/ssalibenjamin0402@gmail.com/meetings"))
                .timeout(Duration.ofHours(1))
                .header("Content-Type", "application/json")
                .header("Authorization","5jhxrZRWAofHJTAQyQsQsS_yATCXgW5RQ")
                .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(meetingInfo)))
                .build();
        CompletableFuture<Void> response = HttpClient.newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::statusCode)
                .thenAccept(System.out::println);
        HashMap<String,String> result = new HashMap<String, String>();
        result.put("result","success");
        result.put("more",response.toString());
        return result;
    };

    public Meetings getMeetingDetails(){
        return null;
    };

}
