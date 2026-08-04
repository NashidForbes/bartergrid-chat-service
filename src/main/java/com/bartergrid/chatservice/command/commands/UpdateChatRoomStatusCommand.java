package com.bartergrid.chatservice.command.commands;

import com.bartergrid.chatservice.coreapi.model.ChatRoomStatus;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

public record UpdateChatRoomStatusCommand(
        @TargetAggregateIdentifier String chatRoomId,
        ChatRoomStatus newStatus,
        String reason,
        Instant timestamp
) {}
