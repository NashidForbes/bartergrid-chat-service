package com.bartergrid.chatservice.coreapi.data.interfaces;

import com.bartergrid.chatservice.coreapi.data.ChatRoomEntity;
import com.bartergrid.chatservice.coreapi.model.ChatRoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IChatRoomEntityRepository extends JpaRepository<ChatRoomEntity, String> {
  ChatRoomEntity findByChatRoomId(String chatRoomId);
  Page<ChatRoomEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);
  Page<ChatRoomEntity> findByChatRoomStatus(ChatRoomStatus chatRoomStatus, Pageable pageable);
}