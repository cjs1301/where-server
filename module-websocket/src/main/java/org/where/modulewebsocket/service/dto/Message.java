package org.where.modulewebsocket.service.dto;


import lombok.Data;

@Data
public class Message {
    private String action;
    private String channelId;
    private LocationDto locationData;
    private MessageDto messageData;
}

