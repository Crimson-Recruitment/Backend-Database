package com.CrimsonBackendDatabase.crimsondb.Meetings;

import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyToken;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenService;
import com.CrimsonBackendDatabase.crimsondb.Utils.MeetingInfo;
import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class MeetingsService {
    private final CompanyTokenService companyTokenService;
    @Autowired
    public MeetingsService(CompanyTokenService companyTokenService) {
        this.companyTokenService = companyTokenService;
    }
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @Transactional
    public HashMap<String, String> scheduleMeeting(String accessToken,MeetingInfo meetingInfo) throws InvalidTokenException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if (companyToken.isPresent()) {
            HttpRequest request = HttpRequest
                    .newBuilder(URI.create("https://api.zoom.us/v2/users/me/meetings"))
                    .header("Content-Type", "application/json")
                    .header("Authorization","Bearer eyJzdiI6IjAwMDAwMSIsImFsZyI6IkhTNTEyIiwidiI6IjIuMCIsImtpZCI6ImFlM2RmYjIyLTM2YmUtNDQ4MC04NzM5LWZhYWQ5ODNlYzNhZSJ9.eyJ2ZXIiOjksImF1aWQiOiI2NDgzMjJmNmQzNDg2MzM3MzliZDY4ZTFiNzBhZTdjYSIsImNvZGUiOiJZVlV3VVUxUUNyblRMdTg1ZHR4UXNhbUZqZElva1VLVmciLCJpc3MiOiJ6bTpjaWQ6TWRPMVRKV0lSeXZVOGtQMld3Y2ciLCJnbm8iOjAsInR5cGUiOjAsInRpZCI6MCwiYXVkIjoiaHR0cHM6Ly9vYXV0aC56b29tLnVzIiwidWlkIjoiVW85OThOdHZSZ2llLVhfNkRHNFFmZyIsIm5iZiI6MTY5NjU5NDgzNywiZXhwIjoxNjk2NTk4NDM3LCJpYXQiOjE2OTY1OTQ4MzcsImFpZCI6Il9uajNWbkpWVE5PWFRFU2xzUXZZWWcifQ.xWT8absW6XnoGTv_Z61rgy7f3iof88v9GcaqtfwqW1WytpnIEf4_hWw2hLlxkC-JibQarUetD6yjty0CX5JmvA")
                    .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(meetingInfo)))
                    .build();
            CompletableFuture<HttpResponse<String>> response = null;
            response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            String body = null;
            int statusCode = 0;
            try{
                body = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                throw new RuntimeException(e);
            }
            HashMap<String,String> result = new HashMap<String, String>();
            result.put("result","success");
            result.put("more",body);
            return result;
        } else {
            throw new InvalidTokenException();
        }

    };

    @Transactional
    public Meetings getMeetingDetails(){
        return null;
    };
    @Transactional
    public HashMap<String,String> getCode(String accessToken, String code) throws InvalidTokenException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if (companyToken.isPresent()) {
            HashMap<String,String> bod = new HashMap<String,String>();
            bod.put("device_code",companyToken.get().getCompany().getZoomCode());
            bod.put("grant_type","urn:ietf:params:oauth:grant-type:device_code");
            HttpRequest request = HttpRequest
                    .newBuilder(URI.create("https://zoom.us/oauth/token"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(bod)))
                    .build();
            CompletableFuture<HttpResponse<String>> response = null;
            response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            String body = null;
            int statusCode = 0;
            try{
                body = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                throw new RuntimeException(e);
            }
            companyToken.get().getCompany().setZoomCode(code);
            HashMap<String,String> result = new HashMap<String, String>();
            result.put("result","success");
            return result;
        } else {
            throw new InvalidTokenException();
        }
    }

}
