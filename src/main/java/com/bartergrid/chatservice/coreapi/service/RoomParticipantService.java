package com.bartergrid.chatservice.coreapi.service;

import com.bartergrid.chatservice.coreapi.data.ChatRoomEntity;
import com.bartergrid.chatservice.coreapi.data.RoomParticipantEntity;
import com.bartergrid.chatservice.coreapi.data.interfaces.IRoomParticipantEntityRepository;
import com.bartergrid.chatservice.coreapi.model.ChatRoomRole;
import com.bartergrid.chatservice.coreapi.service.interfaces.IRoomParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class RoomParticipantService implements IRoomParticipantService {

    private final IRoomParticipantEntityRepository roomParticipantEntityRepository;

    public RoomParticipantService(IRoomParticipantEntityRepository roomParticipantEntityRepository) {
        this.roomParticipantEntityRepository = roomParticipantEntityRepository;
    }

    @Override
    public Set<RoomParticipantEntity> findByRoomRole(ChatRoomRole chatRoomRole) {
        return roomParticipantEntityRepository.findByChatRoomRole(chatRoomRole);
    }

    @Override
    public Set<RoomParticipantEntity> findByUserId(String userId) {
        return roomParticipantEntityRepository.findByUserId(userId);
    }

    @Override
    public RoomParticipantEntity findByLastReadMessageId(String userId) {
        return findByUserId(userId).stream().findFirst().orElse(null);
    }

    @Override
    public RoomParticipantEntity save(RoomParticipantEntity roomParticipantEntity) {
        return roomParticipantEntityRepository.save(roomParticipantEntity);
    }

    @Override
    public RoomParticipantEntity findByChatRoom(ChatRoomEntity chatRoom) {
        return roomParticipantEntityRepository.findByChatRoom(chatRoom);
    }

    @Override
    public RoomParticipantEntity findByUserIdAndChatRoomId(String userId, String chatRoomId) {
        return roomParticipantEntityRepository.findByUserIdAndChatRoomChatRoomId(userId, chatRoomId);
    }

    @Override
    public void delete(RoomParticipantEntity roomParticipantEntity) {
        roomParticipantEntityRepository.delete(roomParticipantEntity);
    }
}