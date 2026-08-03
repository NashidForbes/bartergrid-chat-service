package com.bartergrid.chatservice.coreapi.data;

import com.bartergrid.core.config.interfaces.PaginationCursorEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.bartergrid.chatservice.coreapi.model.MessageType;
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
@Table(name = "message")
@PaginationCursorEntity
public class MessageEntity {

   private static final long serialVersionUID = 1L;

   /**
    * The unique identifier for the description.
    * This is automatically generated as a UUID.
    */
   @Id
   @GeneratedValue(generator = "UUID7")
   @GenericGenerator(name = "UUID7", strategy = "com.bartergrid.core.config.identifier.UUID7Generator")
   @Column(updatable = false)
   private String messageId;

   /**
    * The chatroom information associated with the message.
    */
   @ManyToOne(fetch = FetchType.EAGER, optional = false, targetEntity = ChatRoomEntity.class)
   @JoinColumn(name = "chatroom_chatroom_id", nullable = false)
   @JsonManagedReference
   private ChatRoomEntity chatRoom; // References chat_rooms.id N:1 relationship


   @ManyToOne(fetch = FetchType.EAGER, optional = false, targetEntity = RoomParticipantEntity.class)
   @JoinColumn(name = "sender_user_id", nullable = false)
   @JsonManagedReference
   private RoomParticipantEntity roomParticipant; // sender: References room_participant N:1 relationship

   @Enumerated(EnumType.STRING)
   private MessageType messageType; // 'TEXT', 'SYSTEM_ALERT', 'OFFER_PROPOSAL', 'DEAL_TRIGGER', 'VIDEO', 'AUDIO'

   private String message;

   /**
    * The timestamp when the item was last updated.
    */
   @Column(nullable = false)
   private Instant createdAt;

   @Column(nullable = true, columnDefinition = "jsonb")
   @JdbcTypeCode(SqlTypes.JSON)
   private JsonNode mediaMetaData;

   @Column(nullable = true, columnDefinition = "jsonb")
   @JdbcTypeCode(SqlTypes.JSON)
   private JsonNode metaData; // Additional metadata for the item, can be used for custom fields or even business logic
}