package com.bartergrid.chatservice.coreapi.events;

import com.bartergrid.chatservice.coreapi.model.ChatRoomStatus;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

public record ChatRoomStatusUpdatedEvent(
        @TargetAggregateIdentifier String chatRoomId,
        ChatRoomStatus newStatus,
        Instant timestamp
) {}