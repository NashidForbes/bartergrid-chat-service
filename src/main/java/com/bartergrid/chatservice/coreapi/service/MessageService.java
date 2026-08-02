package com.bartergrid.chatservice.coreapi.service;

import com.bartergrid.chatservice.coreapi.data.MessageEntity;
import com.bartergrid.chatservice.coreapi.service.interfaces.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MessageService implements IMessageService {

    @Override
    public MessageEntity save(MessageEntity messageEntity) {
        return null;
    }

    @Override
    public List<MessageEntity> findByMessageContainingIgnoreCase(String title) {
        return List.of();
    }

    @Override
    public List<MessageEntity> findAll(Pageable pageable) {
        return List.of();
    }

    @Override
    public MessageEntity findByChatRoomId(String chatRoomId) {
        return null;
    }
}