package com.bartergrid.chatservice.command.commands;

import com.bartergrid.chatservice.coreapi.model.MessageType;
import com.fasterxml.jackson.databind.JsonNode;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

public record SendChatMessageCommand(
        @TargetAggregateIdentifier String chatRoomId,
        String messageId,
        String senderId,
        MessageType messageType,
        String message,
        JsonNode mediaMetaData,
        String mediaUrl,
        JsonNode metadata,
        Instant timestamp
) {}
