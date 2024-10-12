package org.where.modulewebsocket.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.locationtech.jts.geom.Coordinate;

public record SocketLocationMessageDto(String sender, Coordinate coordinate) {
    @JsonCreator
    public SocketLocationMessageDto(@JsonProperty("sender") String sender,
                                    @JsonProperty("coordinate") Coordinate coordinate) {
        this.sender = sender;
        this.coordinate = coordinate;
    }
}
