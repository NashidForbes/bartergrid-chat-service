package com.bartergrid.chatservice.coreapi.service.interfaces;

import com.bartergrid.chatservice.coreapi.data.ChatRoomEntity;
import org.springframework.data.domain.Pageable;

public interface IChatRoomService {
    ChatRoomEntity save(ChatRoomEntity chatRoomEntity);
    Pageable findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Pageable findAll(Pageable pageable);
    ChatRoomEntity findByChatRoomId(String chatRoomId);
}