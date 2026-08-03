package com.bartergrid.chatservice.coreapi.service.interfaces;

import com.bartergrid.chatservice.coreapi.data.ChatRoomEntity;
import com.bartergrid.chatservice.coreapi.data.RoomParticipantEntity;
import com.bartergrid.chatservice.coreapi.model.ChatRoomRole;

import java.util.Set;

public interface IRoomParticipantService {
    Set<RoomParticipantEntity> findByRoomRole(ChatRoomRole chatRoomRole);
    Set<RoomParticipantEntity> findByUserId(String userId);
    RoomParticipantEntity findByLastReadMessageId(String userId);
    RoomParticipantEntity save(RoomParticipantEntity roomParticipantEntity);
    RoomParticipantEntity findByChatRoom(ChatRoomEntity chatRoom);
    RoomParticipantEntity findByUserIdAndChatRoomId(String userId, String chatRoomId);
    void delete(RoomParticipantEntity roomParticipantEntity);
}