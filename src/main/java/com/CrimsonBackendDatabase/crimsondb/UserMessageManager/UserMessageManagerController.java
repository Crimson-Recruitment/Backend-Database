package com.CrimsonBackendDatabase.crimsondb.UserMessageManager;

import com.CrimsonBackendDatabase.crimsondb.CompanyMessageManager.CompanyMessageManagerService;
import com.CrimsonBackendDatabase.crimsondb.UserMessageManager.UserMessagesException.InvalidReceiverException;
import com.CrimsonBackendDatabase.crimsondb.UserMessageManager.UserMessagesException.NoChatsException;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.Utils.ChatMessage;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserMessageManagerController {

    private final UserMessageManagerService userMessagesService;
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public UserMessageManagerController(UserMessageManagerService userMessagesService, SimpMessageSendingOperations messagingTemplate) {
        this.userMessagesService = userMessagesService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send-user/{receiverType}/{receiverId}")
    @SendTo("/msg/receive-user/{receiverType}/{receiverId}")
    public List<?> sendMessage(
            @Payload ChatMessage message,
            @Headers Map<String, Object> messageHeaders,
            @DestinationVariable Long receiverId,
            @DestinationVariable String receiverType) {
        Map<String, List<String>> headers = (Map<String, List<String>>) messageHeaders.get("nativeHeaders");
        String accessToken = headers.get(HttpHeaders.AUTHORIZATION).get(0);
        try {
            return userMessagesService.postUserMessage(accessToken,message,receiverId,receiverType);
        } catch (InvalidTokenException | InvalidReceiverException e) {
            HashMap<String, String> err = new HashMap<String, String>();
            err.put("error",e.getMessage());
            messagingTemplate.convertAndSend("/msg/comp/err/ED3", err);
            throw new RuntimeException(e);
        }
    }

    @MessageMapping("/delete-user/{messageId}")
    @SendTo("/msg/del/{receiverType}/{receiverId}")
    public HashMap<String, String> deleteMessage(
            @Headers Map<String, Object> messageHeaders,
            @DestinationVariable Long messageId
    ) {
        Map<String, List<String>> headers = (Map<String, List<String>>) messageHeaders.get("nativeHeaders");
        String accessToken = headers.get(HttpHeaders.AUTHORIZATION).get(0);

        try {
            return userMessagesService.deleteUsersMessage(accessToken, messageId);
        } catch (InvalidReceiverException | InvalidTokenException e) {
            throw new RuntimeException(e);
        }

    }

    @MessageMapping("/clear-user/{receiverType}/{receiverId}")
    @SendTo("/msg/clr/{receiverType}/{receiverId}")
    public HashMap<String, String> clearAllMessages(
            @Headers Map<String, Object> messageHeaders,
            @DestinationVariable Long receiverId,
            @DestinationVariable String receiverType
    ) {
        Map<String, List<String>> headers = (Map<String, List<String>>) messageHeaders.get("nativeHeaders");
        String accessToken = headers.get(HttpHeaders.AUTHORIZATION).get(0);
        try {
            return userMessagesService.clearUsersMessages(accessToken,receiverId,receiverType );
        } catch (InvalidReceiverException | InvalidTokenException | NoChatsException e) {
            throw new RuntimeException(e);
        }

    }


    @SendTo("/msg/user/err/{messageToken}")
    public HashMap<String,String> sendError(HashMap<String, String> err){
        return err;
    }
}
