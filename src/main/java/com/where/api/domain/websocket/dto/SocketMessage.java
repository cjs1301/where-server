package com.where.api.domain.websocket.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SocketMessage {
    private String type;
    private String sender;
    private String channelId;
    private Coordinates coordinates;

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void newConnect(){
        this.type = "new";
    }

    public void closeConnect(){
        this.type = "close";
    }
}
