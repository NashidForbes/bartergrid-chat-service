package com.bartergrid.chatservice.coreapi.events;

import com.bartergrid.chatservice.coreapi.model.MessageType;
import com.fasterxml.jackson.databind.JsonNode;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

public record ChatMessageSentEvent(
        @TargetAggregateIdentifier String chatRoomId,
        String messageId,
        String senderId,
        MessageType messageType,          // e.g., TEXT, IMAGE, DEAL_TRIGGER
        String message,
        JsonNode mediaMetaData,              // Nullable: S3/Object Store Reference ID
        String mediaUrl,           // Nullable: Direct URL / CDN path
        JsonNode metadata, // Flexible payload for extra context
        Instant timestamp
) {}