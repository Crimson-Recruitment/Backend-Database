package com.CrimsonBackendDatabase.crimsondb.CompanyMessages;

import com.CrimsonBackendDatabase.crimsondb.Utils.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;


@Controller
public class CompanyMessagesController {

    private final CompanyMessagesService companyMessagesService;

    @Autowired
    public CompanyMessagesController(CompanyMessagesService companyMessagesService) {
        this.companyMessagesService = companyMessagesService;
    }

    @MessageMapping("/crimsonws/send-message/{receiverType}/{receiverId}")
    @SendTo("/msg/receive-message/{receiverType}/{receiverId}")
    public ChatMessage sendMessage(
            @Payload ChatMessage message,
            SimpMessageHeaderAccessor headerAccessor,
            @DestinationVariable Long receiverId,
            @DestinationVariable String receiverType) {
        String accessToken = (String) headerAccessor.getSessionAttributes().get("username");
      return message;
    }
}
