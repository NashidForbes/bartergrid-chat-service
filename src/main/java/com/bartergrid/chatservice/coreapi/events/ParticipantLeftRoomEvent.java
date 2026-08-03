package com.bartergrid.chatservice.coreapi.events;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

public record ParticipantLeftRoomEvent(
        @TargetAggregateIdentifier String chatRoomId,
        String userId,
        Instant timestamp
) {}