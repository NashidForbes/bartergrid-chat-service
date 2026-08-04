package com.bartergrid.chatservice.coreapi.service;

import com.bartergrid.chatservice.coreapi.data.ChatRoomEntity;
import com.bartergrid.chatservice.coreapi.data.interfaces.IChatRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChatRoomServiceImpl implements com.bartergrid.chatservice.coreapi.service.interfaces.IChatRoomService {

    private final IChatRoomService chatRoomEntityRepository;

    ChatRoomServiceImpl(IChatRoomService chatRoomEntityRepository) {
        this.chatRoomEntityRepository = chatRoomEntityRepository;
    }

    @Override
    public ChatRoomEntity save(ChatRoomEntity chatRoomEntity) {
        return chatRoomEntityRepository.save(chatRoomEntity);
    }

    @Override
    public Page<ChatRoomEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable) {
        return chatRoomEntityRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    @Override
    public Page<ChatRoomEntity> findAll(Pageable pageable) {
        return chatRoomEntityRepository.findAll(pageable);
    }

    @Override
    public ChatRoomEntity findByChatRoomId(String chatRoomId) {
        return chatRoomEntityRepository.findByChatRoomId(chatRoomId);
    }
}