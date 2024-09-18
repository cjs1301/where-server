package org.where.modulewebsocket.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Value
public class SocketMessageDto {
    String sender;
    String message;

    @JsonCreator
    public SocketMessageDto(@JsonProperty("sender") String sender,
                            @JsonProperty("message") String message) {
        this.sender = sender;
        this.message = message;
    }
}
