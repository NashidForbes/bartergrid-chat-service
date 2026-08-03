package com.bartergrid.chatservice.coreapi.events;

import com.bartergrid.chatservice.coreapi.model.ChatRoomType;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

public record ChatRoomCreatedEvent(
        @TargetAggregateIdentifier String chatRoomId,
        ChatRoomType chatRoomType,
        String tradeId,        // Nullable if general chat
        String title,        // Nullable for 1:1 chats
        String directHash,   // Nullable, used for 1:1 uniqueness
        Instant createdAt
) {}