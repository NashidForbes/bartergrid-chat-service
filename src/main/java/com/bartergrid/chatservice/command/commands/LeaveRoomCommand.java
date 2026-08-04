package com.bartergrid.chatservice.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

public record LeaveRoomCommand(
        @TargetAggregateIdentifier String chatRoomId,
        String userId,
        Instant timestamp
) {}
