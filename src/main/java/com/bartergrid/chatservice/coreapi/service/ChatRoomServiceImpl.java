package com.bartergrid.chatservice.coreapi.service;

import com.bartergrid.chatservice.coreapi.data.ChatRoomEntity;
import com.bartergrid.chatservice.coreapi.data.interfaces.IChatRoomEntityRepository;
import com.bartergrid.chatservice.coreapi.service.interfaces.IChatRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChatRoomServiceImpl implements IChatRoomService {

    private final IChatRoomEntityRepository chatRoomEntityRepository;

    ChatRoomServiceImpl(IChatRoomEntityRepository chatRoomEntityRepository) {
        this.chatRoomEntityRepository = chatRoomEntityRepository;
    }

    @Override
    public ChatRoomEntity save(ChatRoomEntity chatRoomEntity) {
        return chatRoomEntityRepository.save(chatRoomEntity);
    }

    @Override
    public Pageable findByTitleContainingIgnoreCase(String title, Pageable pageable) {
        return chatRoomEntityRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    @Override
    public Pageable findAll(Pageable pageable) {
        return (Pageable) chatRoomEntityRepository.findAll();
    }

    @Override
    public ChatRoomEntity findByChatRoomId(String chatRoomId) {
        return chatRoomEntityRepository.findByChatRoomId(chatRoomId);
    }
}