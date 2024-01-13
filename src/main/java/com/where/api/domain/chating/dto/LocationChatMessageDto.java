package com.where.api.domain.chating.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LocationChatMessageDto {
    private String type;
    private String sender;
    private String channelId;
    private CoordinateDto coordinates;

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
