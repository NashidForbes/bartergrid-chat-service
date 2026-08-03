package com.bartergrid.chatservice.query.handler;

import com.bartergrid.chatservice.coreapi.data.ChatRoomEntity;
import com.bartergrid.chatservice.coreapi.data.MessageEntity;
import com.bartergrid.chatservice.coreapi.data.RoomParticipantEntity;
import com.bartergrid.chatservice.coreapi.data.interfaces.IChatRoomService;
import com.bartergrid.chatservice.coreapi.data.interfaces.IMessageService;
import com.bartergrid.chatservice.coreapi.events.*;
import com.bartergrid.chatservice.coreapi.model.ChatRoomStatus;
import com.bartergrid.chatservice.coreapi.service.interfaces.IRoomParticipantService;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomEventHandler {

    private final IChatRoomService chatRoomService;
    private final IMessageService messageService;
    private final IRoomParticipantService roomParticipantService;

    public ChatRoomEventHandler(IChatRoomService chatRoomService, IMessageService messageService, IRoomParticipantService roomParticipantService) {
        this.chatRoomService = chatRoomService;
        this.messageService = messageService;
        this.roomParticipantService = roomParticipantService;
    }

    @EventHandler
    public void on(ChatRoomCreatedEvent event) {
        ChatRoomEntity room = new ChatRoomEntity();
        room.setChatRoomId(event.chatRoomId());
        room.setChatRoomType(event.chatRoomType());
        room.setTradeId(event.tradeId());
        room.setTitle(event.title());
        room.setDirectHash(event.directHash());
        room.setChatRoomStatus(ChatRoomStatus.ACTIVE);
        room.setCreatedAt(event.createdAt());
        room.setUpdatedAt(event.createdAt());
        chatRoomService.save(room);
    }

    @EventHandler
    public void on(ChatMessageSentEvent event) {
        MessageEntity message = new MessageEntity();
        message.setMessageId(event.messageId());

        ChatRoomEntity chatRoom = chatRoomService.findByChatRoomId(event.chatRoomId());
        message.setChatRoom(chatRoom);

        RoomParticipantEntity sender = roomParticipantService.findByUserIdAndChatRoomId(event.senderId(), event.chatRoomId());
        message.setRoomParticipant(sender);

        message.setMessageType(event.messageType());
        message.setMessage(event.message());
        message.setMediaMetaData(event.mediaMetaData());
        message.setMetaData(event.metadata());
        message.setCreatedAt(event.timestamp());
        messageService.save(message);
    }

    @EventHandler
    public void on(ParticipantJoinedRoomEvent event) {
        ChatRoomEntity chatRoom = chatRoomService.findByChatRoomId(event.chatRoomId());

        RoomParticipantEntity participant = new RoomParticipantEntity();
        participant.setRoomParticipantId(event.roomParticipantId());
        participant.setChatRoom(chatRoom);
        participant.setUserId(event.userId());
        participant.setChatRoomRole(event.chatRoomRole());
        participant.setJoinedAt(event.timestamp());
        roomParticipantService.save(participant);
    }

    @EventHandler
    public void on(ParticipantLeftRoomEvent event) {
        RoomParticipantEntity participant = roomParticipantService.findByUserIdAndChatRoomId(event.userId(), event.chatRoomId());
        if (participant != null) {
            roomParticipantService.delete(participant);
        }
    }

    @EventHandler
    public void on(ChatRoomStatusUpdatedEvent event) {
        ChatRoomEntity chatRoom = chatRoomService.findByChatRoomId(event.chatRoomId());
        chatRoom.setChatRoomStatus(event.newStatus());
        chatRoom.setUpdatedAt(event.timestamp());
        chatRoomService.save(chatRoom);
    }

    @EventHandler
    public void on(ParticipantReadReceiptUpdatedEvent event) {
        RoomParticipantEntity participant = roomParticipantService.findByUserIdAndChatRoomId(event.userId(), event.chatRoomId());
        if (participant != null) {
            participant.setLastReadMessageId(event.lastReadMessageId());
            roomParticipantService.save(participant);
        }
    }
}