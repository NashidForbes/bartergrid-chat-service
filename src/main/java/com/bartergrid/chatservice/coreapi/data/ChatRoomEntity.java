package com.bartergrid.chatservice.coreapi.data;

import com.bartergrid.core.config.interfaces.PaginationCursorEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.bartergrid.chatservice.coreapi.model.RoomStatus;
import com.bartergrid.chatservice.coreapi.model.RoomType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_room", indexes = {
/** The index idx_item_pagination_cursor will be created automatically
 from KeysetPaginableEntity (the parent class in core library) **/
        @Index(name = "idx_relationship_direct_hash", columnList = "relationship_direct_hash")
}) // Changed from "order" which is a reserved SQL keyword
@PaginationCursorEntity
public class ChatRoomEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier for the description.
     * This is automatically generated as a UUID.
     */
    @Id
    @GeneratedValue(generator = "UUID7")
    @GenericGenerator(name = "UUID7", strategy = "com.bartergrid.core.config.identifier.UUID7Generator")
    @Column(updatable = false)
    private String chatRoomId;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private String tradeId; // Links the conversation to an active trade/listing aggregate in Axon, refer to Barter-service

    private String title;

    /*** Key Design Tip for 1:1 Chats:
    Using direct_hash prevents creating duplicate 1:1 rooms for the same two users on the same trade.
    When User A clicks "Message User B" on Listing 123, you compute the hash and execute an UPSERT—returning
    the existing room ID if it already exists. ***/

    /*** Deterministic key for 1:1 lookup (e.g., hash(min(userA, userB) + max(userA, userB) + trade_id)) ***/
    private String relationshipDirectHash;

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus; // 'ACTIVE', 'LOCKED', 'ARCHIVED'

    /**
     * The timestamp when the item was created.
     */
    @Column(nullable = false)
    private Instant createdAt;

    /**
     * The timestamp when the item was last updated.
     */
    @Column(nullable = false)
    private Instant updatedAt;

    @Column(nullable = true, columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode metaData;  // Additional metadata for the item, can be used for custom fields or even business logic
}