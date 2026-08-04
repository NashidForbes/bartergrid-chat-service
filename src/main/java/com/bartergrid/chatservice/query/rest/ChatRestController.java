package com.bartergrid.chatservice.query.rest;

import com.bartergrid.chatservice.command.commands.SendChatMessageCommand;
import com.bartergrid.chatservice.coreapi.model.SendMessageRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/rooms")
public class ChatRestController {

    private final CommandGateway commandGateway;

    public ChatRestController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/{chatRoomId}/messages")
    public CompletableFuture<ResponseEntity<String>> sendMessage(
            @PathVariable String chatRoomId,
            @RequestBody SendMessageRequestModel model,
            Principal principal) {

        String messageId = UUID.randomUUID().toString();
        // Fallback to anonymous/system user if SecurityContext/Principal isn't set
        String senderId = (principal != null)
                ? UUID.fromString(principal.getName()).toString()
                : UUID.fromString("00000000-0000-0000-0000-000000000000").toString();

        SendChatMessageCommand command = new SendChatMessageCommand(
                chatRoomId,
                messageId,
                senderId,
                model.type(),
                model.content(),
                model.mediaMetaData(),
                model.mediaUrl(),
                model.metadata(),
                Instant.now()
        );

        // Dispatch command to Axon Server asynchronously
        return commandGateway.send(command)
                .thenApply(result -> ResponseEntity.accepted().body(messageId));
    }
}