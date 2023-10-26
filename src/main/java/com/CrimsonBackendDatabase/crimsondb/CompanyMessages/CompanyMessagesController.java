package com.CrimsonBackendDatabase.crimsondb.CompanyMessages;

import com.CrimsonBackendDatabase.crimsondb.UserMessages.UserMessagesException.InvalidReceiverException;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.Utils.ChatMessage;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class CompanyMessagesController {

    private final CompanyMessagesService companyMessagesService;
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public CompanyMessagesController(CompanyMessagesService companyMessagesService, SimpMessageSendingOperations messagingTemplate) {
        this.companyMessagesService = companyMessagesService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send-message/{receiverType}/{receiverId}")
    @SendTo("/msg/receive-message/{receiverType}/{receiverId}")
    public List<String> sendMessage(
            @Payload ChatMessage message,
            @Headers Map<String, Object> messageHeaders,
            @DestinationVariable Long receiverId,
            @DestinationVariable String receiverType) {
        Map<String, List<String>> headers = (Map<String, List<String>>) messageHeaders.get("nativeHeaders");
        String accessToken = headers.get(HttpHeaders.AUTHORIZATION).get(0);
        try {
            return companyMessagesService.postCompanyMessage(accessToken,message,receiverId,receiverType);
        } catch (InvalidTokenException | InvalidReceiverException e) {
            HashMap<String, String> err = new HashMap<String, String>();
            err.put("error",e.getMessage());
            messagingTemplate.convertAndSend("/msg/comp/err/ED3", err);
            throw new RuntimeException(e);
        }
    }

    @SendTo("/msg/comp/err/{messageToken}")
    public HashMap<String,String> sendError(HashMap<String, String> err){
        return err;
    }
}
