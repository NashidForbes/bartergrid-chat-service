package com.bartergrid.chatservice.coreapi.events;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

public record ParticipantReadReceiptUpdatedEvent(
        @TargetAggregateIdentifier String chatRoomId,
        String userId,
        String lastReadMessageId,
        Instant timestamp
) {}