package org.where.modulewebsocket.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record SocketLocationMessageDto(String sender, double latitude, double longitude) {
    @JsonCreator
    public SocketLocationMessageDto(@JsonProperty("sender") String sender,
                                    @JsonProperty("latitude") double latitude,
                                    @JsonProperty("longitude") double longitude
    ) {
        this.sender = sender;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
