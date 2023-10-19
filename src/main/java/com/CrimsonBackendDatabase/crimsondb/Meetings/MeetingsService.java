package com.CrimsonBackendDatabase.crimsondb.Meetings;

import com.CrimsonBackendDatabase.crimsondb.Applications.Applications;
import com.CrimsonBackendDatabase.crimsondb.Applications.ApplicationsException.InvalidApplicationException;
import com.CrimsonBackendDatabase.crimsondb.Applications.ApplicationsRepository;
import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyToken;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenService;
import com.CrimsonBackendDatabase.crimsondb.Meetings.MeetingsException.InvalidMeetingException;
import com.CrimsonBackendDatabase.crimsondb.Meetings.MeetingsException.ZoomAuthorizationException;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import com.CrimsonBackendDatabase.crimsondb.Users.UsersException.InvalidUserException;
import com.CrimsonBackendDatabase.crimsondb.Users.UsersRepository;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
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
    private final UsersRepository usersRepository;
    private final ApplicationsRepository applicationsRepository;
    private final MeetingsRepository meetingsRepository;
    @Autowired
    public MeetingsService(CompanyTokenService companyTokenService, UsersRepository usersRepository, MeetingsRepository meetingsRepository, ApplicationsRepository applicationsRepository) {
        this.companyTokenService = companyTokenService;
        this.usersRepository = usersRepository;
        this.meetingsRepository = meetingsRepository;
        this.applicationsRepository = applicationsRepository;
    }
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @Transactional
    public HashMap<String, Object> scheduleMeeting(String accessToken,MeetingInfo meetingInfo, Long applicationId) throws InvalidTokenException, IOException, InterruptedException, ParseException, InvalidApplicationException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if (companyToken.isPresent()) {
            Optional<Applications> applications = applicationsRepository.findById(applicationId);
            if(applications.isPresent()) {
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
                String DEFAULT_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
                int statusCode = 0;
                DateFormat formatter = new SimpleDateFormat(DEFAULT_PATTERN);
                body = new Gson().fromJson(response.body(), new TypeToken<HashMap<String, Object>>(){}.getType());
                Meetings meeting = new Meetings(
                        body.get("join_url").toString(),
                        body.get("agenda").toString(),
                        Date.from(Instant.parse(body.get("start_time").toString())),
                                body.get("h323_password").toString(),
                                body.get("timezone").toString(),
                                applications.get()
                        );
                meetingsRepository.save(meeting);
                HashMap<String,Object> result = new HashMap<>();
                result.put("result","success");
                return result;
            } else {
                throw new InvalidApplicationException();
            }
        } else {
            throw new InvalidTokenException();
        }

    };

    @Transactional
    public Meetings getMeetingDetails(String accessToken, Long meetingId) throws InvalidMeetingException, InvalidTokenException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if(companyToken.isPresent()) {
            Optional<Meetings> meeting = meetingsRepository.findById(meetingId);
            if(meeting.isPresent()) {
                return meeting.get();
            } else {
                throw new InvalidMeetingException("Meeting with Id "+meetingId+" doesn't exist!");
            }
        } else {
            throw new InvalidTokenException();
        }
    };

    @Transactional
    public HashMap<String,String> refreshAccessToken(String accessToken) throws InvalidTokenException, IOException, InterruptedException, ZoomAuthorizationException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if (companyToken.isPresent()) {
            Company company = companyToken.get().getCompany();
            String urlParameters = "refresh_token="+company.getZoomRefreshToken()+"&grant_type=refresh_token";
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

        } else  {
            throw new InvalidTokenException();
        }
    }

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
