package com.CrimsonBackendDatabase.crimsondb.Meetings;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyToken;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenService;
import com.CrimsonBackendDatabase.crimsondb.Meetings.MeetingsException.ZoomAuthorizationException;
import com.CrimsonBackendDatabase.crimsondb.Utils.MeetingInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class MeetingsService {

    @Value("${zoom.client.id}")
    private String zoomClientId;
    @Value("${zoom.client.secret}")
    private String zoomClientSecret;
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
    public HashMap<String, Object> scheduleMeeting(String accessToken,MeetingInfo meetingInfo) throws InvalidTokenException, IOException, InterruptedException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if (companyToken.isPresent()) {
            Company company = companyToken.get().getCompany();
            HttpRequest request = HttpRequest
                    .newBuilder(URI.create("https://api.zoom.us/v2/users/me/meetings"))
                    .header("Content-Type", "application/json")
                    .header("Authorization","Bearer "+company.getZoomAccessToken())
                    .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(meetingInfo)))
                    .build();
            HttpResponse<String> response = null;
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            HashMap<String, Object> body = null;
            int statusCode = 0;
            body = new Gson().fromJson(response.body(), new TypeToken<HashMap<String, Object>>(){}.getType());
            HashMap<String,Object> result = new HashMap<>();
            result.put("result","success");
            result.putAll(body);
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
    public HashMap<String,String> getCode(String accessToken, String code) throws InvalidTokenException, ExecutionException, InterruptedException, IOException, ZoomAuthorizationException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if (companyToken.isPresent()) {
            Company company = companyToken.get().getCompany();
            String urlParameters = "code="+code+"&grant_type=authorization_code&redirect_uri=http://127.0.0.1:8081/meeting/get-code";
            HttpRequest zoomAuth = HttpRequest
                    .newBuilder(URI.create("https://zoom.us/oauth/token"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Authorization","Basic "+ Base64.getEncoder().encodeToString((zoomClientId+":"+zoomClientSecret).getBytes()))
                    .POST(HttpRequest.BodyPublishers.ofString(urlParameters))
                    .build();
            HttpResponse<String> response = null;
            response = httpClient.send(zoomAuth, HttpResponse.BodyHandlers.ofString());
            HashMap<String, String> body = null;
            int statusCode = 0;
            body = new Gson().fromJson(response.body(), new TypeToken<HashMap<String, String>>(){}.getType());
            if(body.get("error") == null) {
                company.setZoomAccessToken(body.get("access_token"));
                company.setZoomRefreshToken(body.get("refresh_token"));
                HashMap<String,String> result = new HashMap<String, String>();
                result.put("result","success");
                return result;
            } else {
                throw new ZoomAuthorizationException("Zoom Error: "+body.get("error")+" Reason: "+body.get("reason"));
            }
        } else {
            throw new InvalidTokenException();
        }
    }

}
