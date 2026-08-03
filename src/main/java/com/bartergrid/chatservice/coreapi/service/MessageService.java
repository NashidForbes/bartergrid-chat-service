package com.bartergrid.chatservice.coreapi.service;

import com.bartergrid.chatservice.coreapi.data.MessageEntity;
import com.bartergrid.chatservice.coreapi.data.interfaces.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MessageService implements com.bartergrid.chatservice.coreapi.service.interfaces.IMessageService {

    private final IMessageService messageEntityRepository;

    public MessageService(IMessageService messageEntityRepository) {
        this.messageEntityRepository = messageEntityRepository;
    }

    @Override
    public MessageEntity save(MessageEntity messageEntity) {
        return messageEntityRepository.save(messageEntity);
    }

    @Override
    public List<MessageEntity> findByMessageContainingIgnoreCase(String title) {
        return (List<MessageEntity>) messageEntityRepository.findByMessageContainingIgnoreCase(title);
    }

    @Override
    public List<MessageEntity> findAll(Pageable pageable) {
        return messageEntityRepository.findAll();
    }

    @Override
    public List<MessageEntity> findByChatRoomId(String chatRoomId) {
        return messageEntityRepository.findByChatRoomId(chatRoomId);
    }

    @Override
    public MessageEntity findByMessageId(String messageId) {
        return messageEntityRepository.findByMessageId(messageId);
    }
}