package com.bartergrid.chatservice.coreapi.service;

import com.bartergrid.chatservice.coreapi.data.ChatRoomEntity;
import com.bartergrid.chatservice.coreapi.service.interfaces.IChatRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChatRoomServiceImpl implements IChatRoomService {

    ChatRoomServiceImpl() {
    }


    @Override
    public ChatRoomEntity save(ChatRoomEntity chatRoomEntity) {
        return null;
    }

    @Override
    public List<ChatRoomEntity> findByTitleContainingIgnoreCase(String title) {
        return List.of();
    }

    @Override
    public List<ChatRoomEntity> findAll(Pageable pageable) {
        return List.of();
    }

    @Override
    public ChatRoomEntity findByChatRoomId(String chatRoomId) {
        return null;
    }
}