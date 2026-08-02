package com.bartergrid.chatservice.coreapi.service;

import com.bartergrid.chatservice.coreapi.data.RoomParticipantEntity;
import com.bartergrid.chatservice.coreapi.data.interfaces.IRoomParticipantEntityRepository;
import com.bartergrid.chatservice.coreapi.model.RoomRole;
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
    public Set<RoomParticipantEntity> findByRoomRole(RoomRole roomRole) {
        return roomParticipantEntityRepository.findByRoomRole(roomRole);
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
}