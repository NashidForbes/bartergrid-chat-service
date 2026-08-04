package com.bartergrid.chatservice.coreapi.data.interfaces;

import com.bartergrid.chatservice.coreapi.data.ChatRoomEntity;
import com.bartergrid.chatservice.coreapi.data.RoomParticipantEntity;
import com.bartergrid.chatservice.coreapi.model.ChatRoomRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IRoomParticipantEntityRepository extends JpaRepository<RoomParticipantEntity, String> {
     Set<RoomParticipantEntity> findByChatRoomRole(ChatRoomRole chatRoomRole);
     Set<RoomParticipantEntity> findByUserId(String userId);
     RoomParticipantEntity findByLastReadMessageId(String userId);
     RoomParticipantEntity findByChatRoom(ChatRoomEntity chatRoom);
     RoomParticipantEntity findByUserIdAndChatRoomChatRoomId(String userId, String chatRoomId);
}