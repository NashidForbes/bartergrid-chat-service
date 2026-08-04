package com.bartergrid.chatservice.query.rest;

import com.bartergrid.chatservice.command.commands.SendChatMessageCommand;
import com.bartergrid.chatservice.coreapi.model.SendMessageRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.Instant;
import java.util.UUID;

@Controller
public class ChatWebSocketController {

    private final CommandGateway commandGateway;

    public ChatWebSocketController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    /**
     * Client sends to destination: /app/rooms/{roomId}/send
     */
    @MessageMapping("/rooms/{roomId}/send")
    public void handleIncomingWebSocketMessage(
            @DestinationVariable String roomId,
            @Payload SendMessageRequestModel model,
            Principal principal) {

        String messageId = UUID.randomUUID().toString();
        String senderId = (principal != null)
                ? principal.getName()
                : "00000000-0000-0000-0000-000000000000";

        SendChatMessageCommand command = new SendChatMessageCommand(
                roomId,
                messageId,
                senderId,
                model.type(),
                model.content(),
                model.mediaMetaData(),
                model.mediaUrl(),
                model.metadata(),
                Instant.now()
        );

        // Fire-and-forget command dispatch to Axon Aggregate
        commandGateway.send(command);
    }
}