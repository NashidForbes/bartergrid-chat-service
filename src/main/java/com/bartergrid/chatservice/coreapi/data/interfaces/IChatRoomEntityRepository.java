package com.bartergrid.chatservice.coreapi.data.interfaces;

import com.bartergrid.chatservice.coreapi.data.ChatRoomEntity;
import com.bartergrid.chatservice.coreapi.model.ChatRoomStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IChatRoomEntityRepository extends JpaRepository<ChatRoomEntity, String> {
  ChatRoomEntity findByChatRoomId(String chatRoomId);
  Pageable findByTitleContainingIgnoreCase(String title, Pageable pageable);
  Pageable findByChatRoomStatus(ChatRoomStatus chatRoomStatus, Pageable pageable);
}