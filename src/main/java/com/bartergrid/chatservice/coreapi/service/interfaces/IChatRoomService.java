package com.bartergrid.chatservice.coreapi.service.interfaces;

import com.bartergrid.chatservice.coreapi.data.ChatRoomEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IChatRoomService {
    ChatRoomEntity save(ChatRoomEntity chatRoomEntity);
    List<ChatRoomEntity> findByTitleContainingIgnoreCase(String title);
    List<ChatRoomEntity> findAll(Pageable pageable);
    ChatRoomEntity findByChatRoomId(String chatRoomId);
}