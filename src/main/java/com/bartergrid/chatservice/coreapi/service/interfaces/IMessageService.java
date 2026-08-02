package com.bartergrid.chatservice.coreapi.service.interfaces;

import com.bartergrid.chatservice.coreapi.data.MessageEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMessageService {
    MessageEntity save(MessageEntity messageEntity);
    List<MessageEntity> findByMessageContainingIgnoreCase(String title);
    List<MessageEntity> findAll(Pageable pageable);
    List<MessageEntity> findByChatRoomId(String chatRoomId);
    MessageEntity findByMessageId(String messageId);
}