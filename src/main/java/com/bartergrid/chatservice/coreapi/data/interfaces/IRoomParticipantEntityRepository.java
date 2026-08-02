package com.bartergrid.chatservice.coreapi.data.interfaces;

import com.bartergrid.chatservice.coreapi.data.RoomParticipantEntity;
import com.bartergrid.chatservice.coreapi.model.RoomRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IRoomParticipantEntityRepository extends JpaRepository<RoomParticipantEntity, String> {
     Set<RoomParticipantEntity> findByRoomRole(RoomRole roomRole);
     Set<RoomParticipantEntity> findByUserId(String userId);
     RoomParticipantEntity findByLastReadMessageId(String userId);
}