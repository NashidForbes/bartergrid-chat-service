package com.bartergrid.chatservice.coreapi.data;

import com.bartergrid.core.config.interfaces.PaginationCursorEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.bartergrid.chatservice.coreapi.model.ChatRoomRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room_participant")
@PaginationCursorEntity
public class RoomParticipantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier for the description.
     * This is automatically generated as a UUID.
     */
    @Id
    @GeneratedValue(generator = "UUID7")
    @GenericGenerator(name = "UUID7", strategy = "com.bartergrid.core.config.identifier.UUID7Generator")
    @Column(updatable = false)
    private String roomParticipantId;

    @OneToOne(fetch = FetchType.EAGER, optional = false, targetEntity = ChatRoomEntity.class, cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "chat_room_id", nullable = false)
    @JsonManagedReference
    private ChatRoomEntity chatRoom;

    private String userId;

    @Enumerated(EnumType.STRING)
    private ChatRoomRole chatRoomRole;

    private String lastReadMessageId;

    private Instant mutedUntil;

    private Instant joinedAt;

    @Column(nullable = true, columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode metaData;
}