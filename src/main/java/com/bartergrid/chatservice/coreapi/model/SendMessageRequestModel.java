package com.bartergrid.chatservice.coreapi.model;

import com.fasterxml.jackson.databind.JsonNode;

public record SendMessageRequestModel(
        MessageType type,
        String content,
        JsonNode mediaMetaData,
        String mediaUrl,
        JsonNode metadata
) {
    public SendMessageRequestModel {
        if (type == null) type = MessageType.TEXT;
    }
}