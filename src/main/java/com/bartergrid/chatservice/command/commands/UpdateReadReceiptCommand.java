package com.bartergrid.chatservice.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

public record UpdateReadReceiptCommand(
        @TargetAggregateIdentifier String chatRoomId,
        String userId,
        String lastReadMessageId,
        Instant timestamp
) {}
