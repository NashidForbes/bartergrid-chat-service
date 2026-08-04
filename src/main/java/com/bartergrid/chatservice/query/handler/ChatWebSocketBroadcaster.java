package com.bartergrid.chatservice.query.handler;

import com.bartergrid.chatservice.coreapi.events.ChatMessageSentEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ChatWebSocketBroadcaster {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatWebSocketBroadcaster(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventHandler
    public void on(ChatMessageSentEvent event) {
        String destination = "/topic/rooms/" + event.chatRoomId();

        // Broadcast event payload to STOMP room topic
        messagingTemplate.convertAndSend(destination, event);
    }
}