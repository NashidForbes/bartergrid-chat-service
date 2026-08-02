package com.bartergrid.chatservice.coreapi.data;

import com.bartergrid.core.config.interfaces.PaginationCursorEntity;
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

   private String chatRoomId;

   private String userId;

   private MessageType messageType;

   private String message;

   @Column(nullable = true, columnDefinition = "jsonb")
   @JdbcTypeCode(SqlTypes.JSON)
   private JsonNode metaData;
}