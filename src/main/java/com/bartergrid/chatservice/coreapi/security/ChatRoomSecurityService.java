package com.bartergrid.chatservice.coreapi.security;

import com.bartergrid.chatservice.coreapi.data.ChatRoomEntity;
import com.bartergrid.chatservice.coreapi.data.MessageEntity;
import com.bartergrid.chatservice.coreapi.data.RoomParticipantEntity;
import com.bartergrid.chatservice.coreapi.service.interfaces.IChatRoomService;
import com.bartergrid.chatservice.coreapi.service.interfaces.IMessageService;
import com.bartergrid.chatservice.coreapi.service.interfaces.IRoomParticipantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static com.bartergrid.core.security.JwtAuthorities.JwtUtils.extractUserId;

@Service  // Replace with the actual service name if necessary))
@Slf4j
public class ChatRoomSecurityService {
    private final IChatRoomService chatRoomService;
    private final IRoomParticipantService roomParticipantService;

    public ChatRoomSecurityService(IChatRoomService chatRoomService, IRoomParticipantService roomParticipantService) {
        this.chatRoomService = chatRoomService;
        this.roomParticipantService = roomParticipantService;
    }

    /**
     * Checks if the provided authentication contains a user ID that is the owner of the specified item.
     *
     * @param authentication The authentication object to extract user ID from
     * @param itemId         The ID of the item to check ownership for
     * @return true if the user is the owner of the item, false otherwise
     */
    public boolean isItemOwner(Authentication authentication, String itemId) {
        if (authentication == null || itemId.isBlank()) {
            log.error("Error: Invalid input parameters for isItemOwner, authentication object or item Id is null and/or blank");
            return false;
        }

        String userId = extractUserId(authentication);

        if (userId.isBlank()) {
            log.error("Error: Could not extract user ID from authentication");
            return false;
        }

        return isItemOwner(userId, itemId);
    }

    /**
     * Checks if the provided user ID is the owner of the specified item.
     *
     * @param userId The ID of the user to check
     * @param itemId The ID of the item to check ownership for
     * @return true if the user is the owner of the item, false otherwise
     */
    public boolean isItemOwner(String userId, String itemId) {
        if (userId.isBlank() || itemId.isBlank()) {
            log.error("Error: Invalid input parameters for isItemOwner, user Id or item Id is null or blank");
            return false;
        }


        ChatRoomEntity chatRoom = chatRoomService.findByChatRoomId(itemId);
        RoomParticipantEntity item = roomParticipantService.findByChatRoom(chatRoom);

        if (item == null) {
            log.error("Error: Item not found for item Id {} for user Id {}", itemId, userId);
            return false;
        }

        return userId.equals(item.getUserId());
    }
}