package com.bartergrid.chatservice.coreapi.events;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

public record TradeDealPhraseDetectedEvent(
        @TargetAggregateIdentifier String chatRoomId,
        String tradeId,
        String messageId,
        String triggeredByUserId,
        String matchedPhrase,
        Instant timestamp
) {}