package com.bartergrid.chatservice.coreapi.events;

import com.bartergrid.chatservice.coreapi.model.ChatRoomRole;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

public record ParticipantJoinedRoomEvent(
        @TargetAggregateIdentifier String chatRoomId,
        String roomParticipantId,
        String userId,
        ChatRoomRole chatRoomRole, // e.g., "BIDDER", "SELLER", "MEMBER"
        Instant timestamp
) {}