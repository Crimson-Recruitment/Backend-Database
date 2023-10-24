package com.CrimsonBackendDatabase.crimsondb.CompanyMessages;

import com.CrimsonBackendDatabase.crimsondb.UserMessages.UserMessagesException.InvalidReceiverException;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.Utils.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;


@Controller
public class CompanyMessagesController {

    private final CompanyMessagesService companyMessagesService;

    @Autowired
    public CompanyMessagesController(CompanyMessagesService companyMessagesService) {
        this.companyMessagesService = companyMessagesService;
    }


    @MessageMapping("/send-message/{receiverType}/{receiverId}")
    @SendTo("/msg/receive-message/{receiverId}")
    public ChatMessage sendMessage(
            @Payload ChatMessage message,
            SimpMessageHeaderAccessor headerAccessor,
            @DestinationVariable Long receiverId,
            @DestinationVariable String receiverType) {
        String accessToken = (String) headerAccessor.getSessionAttributes().get("accessToken");
        try {
            return companyMessagesService.postCompanyMessage(accessToken,message,receiverId,receiverType);
        } catch (InvalidTokenException | InvalidReceiverException e) {
            throw new RuntimeException(e);
        }
    }

}
