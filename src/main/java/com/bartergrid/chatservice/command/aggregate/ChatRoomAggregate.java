package com.bartergrid.chatservice.command.aggregate;

import com.bartergrid.chatservice.command.commands.*;
import com.bartergrid.chatservice.coreapi.events.*;
import com.bartergrid.chatservice.coreapi.model.ChatRoomRole;
import com.bartergrid.chatservice.coreapi.model.ChatRoomStatus;
import com.bartergrid.chatservice.coreapi.model.ChatRoomType;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;
import java.util.*;

@Aggregate
public class ChatRoomAggregate {

    @AggregateIdentifier
    private String chatRoomId;
    private ChatRoomType chatRoomType;
    private String tradeId;
    private ChatRoomStatus chatRoomStatus;
    private final Set<String> participants = new HashSet<>();

    // Default constructor required by Axon for Event Sourcing hydration
    protected ChatRoomAggregate() {}

    // =========================================================================
    // COMMAND HANDLERS
    // =========================================================================

    /**
     * Creation Command Handler - Initializes a new Chat Room (1:1, Auction, or Group)
     */
    @CommandHandler
    public ChatRoomAggregate(CreateChatRoomCommand command) {
        if (command.chatRoomId() == null) {
            throw new IllegalArgumentException("Room ID cannot be null.");
        }
        if (command.createdBy() == null) {
            throw new IllegalArgumentException("Creator User ID cannot be null.");
        }

        Instant eventTime = command.timestamp() != null ? command.timestamp() : Instant.now();

        AggregateLifecycle.apply(new ChatRoomCreatedEvent(
                command.chatRoomId(),
                command.chatRoomType(),
                command.tradeId(),
                command.title(),
                command.directHash(),
                eventTime
        ));

        // Automatically join the creator as an initial room participant
        AggregateLifecycle.apply(new ParticipantJoinedRoomEvent(
                command.chatRoomId(),
                UUID.randomUUID().toString(),
                command.createdBy(),
                ChatRoomRole.MEMBER,
                eventTime
        ));
    }

    /**
     * Send Message Command Handler
     */
    @CommandHandler
    public void handle(SendChatMessageCommand command) {
        if (this.chatRoomStatus == ChatRoomStatus.LOCKED || this.chatRoomStatus == ChatRoomStatus.ARCHIVED) {
            throw new IllegalStateException("Cannot send messages in a " + this.chatRoomStatus + " chat room.");
        }
        if (!this.participants.contains(command.senderId())) {
            throw new IllegalStateException("User " + command.senderId() + " is not a member of room " + this.chatRoomId);
        }

        Instant eventTime = command.timestamp() != null ? command.timestamp() : Instant.now();

        AggregateLifecycle.apply(new ChatMessageSentEvent(
                this.chatRoomId,
                command.messageId(),
                command.senderId(),
                command.messageType(),
                command.message(),
                command.mediaMetaData(),
                command.mediaUrl(),
                command.metadata(),
                eventTime
        ));

        // Pattern Intent Detection ("It is a deal!")
        if (isDealConfirmationPhrase(command.message())) {
            AggregateLifecycle.apply(new TradeDealPhraseDetectedEvent(
                    this.chatRoomId,
                    this.tradeId,
                    command.messageId(),
                    command.senderId(),
                    command.message(),
                    eventTime
            ));
        }
    }

    /**
     * Join Room Command Handler (Used for Auctions or Group Barters)
     */
    @CommandHandler
    public void handle(JoinRoomCommand command) {
        if (this.chatRoomStatus != ChatRoomStatus.ACTIVE) {
            throw new IllegalStateException("Cannot join an inactive room.");
        }
        if (this.chatRoomType == ChatRoomType.DIRECT && this.participants.size() >= 2) {
            throw new IllegalStateException("Cannot add more than two participants to a DIRECT 1:1 chat room.");
        }
        if (this.participants.contains(command.userId())) {
            return; // Idempotent check: User is already in the room
        }

        AggregateLifecycle.apply(new ParticipantJoinedRoomEvent(
                this.chatRoomId,
                command.roomParticipantId(),
                command.userId(),
                command.chatRoomRole() != null ? command.chatRoomRole() : ChatRoomRole.MEMBER,
                command.timestamp() != null ? command.timestamp() : Instant.now()
        ));
    }

    /**
     * Leave Room Command Handler
     */
    @CommandHandler
    public void handle(LeaveRoomCommand command) {
        if (!this.participants.contains(command.userId())) {
            return; // Idempotent check: User is not in the room
        }

        AggregateLifecycle.apply(new ParticipantLeftRoomEvent(
                this.chatRoomId,
                command.userId(),
                command.timestamp() != null ? command.timestamp() : Instant.now()
        ));
    }

    /**
     * Update Room Status Command Handler (Lock/Archive)
     */
    @CommandHandler
    public void handle(UpdateChatRoomStatusCommand command) {
        if (this.chatRoomStatus == command.newStatus()) {
            return; // No status change required
        }

        AggregateLifecycle.apply(new ChatRoomStatusUpdatedEvent(
                this.chatRoomId,
                command.newStatus(),
                command.timestamp() != null ? command.timestamp() : Instant.now()
        ));
    }

    /**
     * Update Read Receipt Command Handler
     */
    @CommandHandler
    public void handle(UpdateReadReceiptCommand command) {
        if (!this.participants.contains(command.userId())) {
            throw new IllegalStateException("User " + command.userId() + " is not a member of room " + this.chatRoomId);
        }

        AggregateLifecycle.apply(new ParticipantReadReceiptUpdatedEvent(
                this.chatRoomId,
                command.userId(),
                command.lastReadMessageId(),
                command.timestamp() != null ? command.timestamp() : Instant.now()
        ));
    }

    // =========================================================================
    // EVENT SOURCING HANDLERS
    // =========================================================================

    @EventSourcingHandler
    public void on(ChatRoomCreatedEvent event) {
        this.chatRoomId = event.chatRoomId();
        this.chatRoomType = event.chatRoomType();
        this.tradeId = event.tradeId();
        this.chatRoomStatus = ChatRoomStatus.ACTIVE;
    }

    @EventSourcingHandler
    public void on(ParticipantJoinedRoomEvent event) {
        this.participants.add(event.userId());
    }

    @EventSourcingHandler
    public void on(ParticipantLeftRoomEvent event) {
        this.participants.remove(event.userId());
    }

    @EventSourcingHandler
    public void on(ChatRoomStatusUpdatedEvent event) {
        this.chatRoomStatus = event.newStatus();
    }

    // =========================================================================
    // UTILITY METHODS
    // =========================================================================

    private boolean isDealConfirmationPhrase(String content) {
        if (content == null || content.isBlank()) {
            return false;
        }
        String normalized = content.trim().toLowerCase();
        return normalized.contains("it is a deal")
                || normalized.contains("deal confirmed")
                || normalized.contains("i accept the barter");
    }
}