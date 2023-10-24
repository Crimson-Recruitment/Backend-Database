package com.CrimsonBackendDatabase.crimsondb.CompanyMessages;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.HashMap;

@Controller
public class CompanyMessagesController {

    @MessageMapping("/send-message")
    @SendTo("/receive-message")
    public HashMap<String, String> sendMessage(String message, String accessToken) {

    }

}
