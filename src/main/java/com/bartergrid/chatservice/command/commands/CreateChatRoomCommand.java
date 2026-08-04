package com.bartergrid.chatservice.command.commands;

import com.bartergrid.chatservice.coreapi.model.ChatRoomType;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

public record CreateChatRoomCommand(
        @TargetAggregateIdentifier String chatRoomId,
        ChatRoomType chatRoomType,
        String tradeId,
        String title,
        String directHash,
        String createdBy,
        Instant timestamp
) {}
