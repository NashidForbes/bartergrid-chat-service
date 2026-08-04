package com.bartergrid.chatservice.command.commands;

import com.bartergrid.chatservice.coreapi.model.ChatRoomRole;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

public record JoinRoomCommand(
        @TargetAggregateIdentifier String chatRoomId,
        String roomParticipantId,
        String userId,
        ChatRoomRole chatRoomRole,
        Instant timestamp
) {}
