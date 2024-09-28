package org.where.modulewebsocket.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record SocketChatMessageDto(String sender, String message) {
    @JsonCreator
    public SocketChatMessageDto(@JsonProperty("sender") String sender,
                                @JsonProperty("message") String message) {
        this.sender = sender;
        this.message = message;
    }
}
