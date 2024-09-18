package org.where.modulewebsocket.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BroadcastMessage {
    private String type;
    private Object data;
}
