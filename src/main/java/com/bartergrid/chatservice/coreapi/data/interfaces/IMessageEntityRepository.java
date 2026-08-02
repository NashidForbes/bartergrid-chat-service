package com.bartergrid.chatservice.coreapi.data.interfaces;

import com.bartergrid.chatservice.coreapi.data.ChatRoomEntity;
import com.bartergrid.chatservice.coreapi.data.MessageEntity;
import com.bartergrid.chatservice.coreapi.model.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IMessageEntityRepository extends JpaRepository<ChatRoomEntity, String> {
  Set<MessageEntity> findByMessageContainingIgnoreCase(String message);
  Set<MessageEntity> findByMessageType(MessageType messageType);

}