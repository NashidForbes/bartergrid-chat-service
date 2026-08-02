package com.bartergrid.chatservice.coreapi.service.interfaces;

import com.bartergrid.chatservice.coreapi.data.RoomParticipantEntity;
import com.bartergrid.chatservice.coreapi.model.RoomRole;

import java.util.Set;

public interface IRoomParticipantService {
    Set<RoomParticipantEntity> findByRoomRole(RoomRole roomRole);
    Set<RoomParticipantEntity> findByUserId(String userId);
    RoomParticipantEntity findByLastReadMessageId(String userId);
    RoomParticipantEntity save(RoomParticipantEntity roomParticipantEntity);
}