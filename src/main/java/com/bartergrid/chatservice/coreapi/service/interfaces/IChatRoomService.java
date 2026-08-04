package com.bartergrid.chatservice.coreapi.service.interfaces;

import com.bartergrid.chatservice.coreapi.data.ChatRoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IChatRoomService {
    ChatRoomEntity save(ChatRoomEntity chatRoomEntity);
    Page<ChatRoomEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<ChatRoomEntity> findAll(Pageable pageable);
    ChatRoomEntity findByChatRoomId(String chatRoomId);
}