package com.CrimsonBackendDatabase.crimsondb.Config;

import com.CrimsonBackendDatabase.crimsondb.Utils.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("accessToken");
        if (username != null) {
            log.info("user disconnected");
            var chatMessage = ChatMessage.builder()
                    .id(-1)
                    .message("Bye bye")
                    .build();
            messagingTemplate.convertAndSend("/all", chatMessage);
        }
    }
}
