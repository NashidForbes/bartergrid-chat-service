package com.bartergrid.chatservice.coreapi.data.interfaces;

import com.bartergrid.chatservice.coreapi.data.ChatRoomEntity;
import com.bartergrid.chatservice.coreapi.model.RoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IChatRoomEntityRepository extends JpaRepository<ChatRoomEntity, String> {
  ChatRoomEntity findByRoomId(String roomId);
  Page<ChatRoomEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);
  Page<ChatRoomEntity> findByRoomStatus(RoomStatus roomStatus, Pageable pageable);
}