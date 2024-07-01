package com.where.server.api.service.channel.dto;

import com.where.server.domain.channel.MessageEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageDto {
    String id;
    String channelId;
    String sender;
    String message;
    String createdAt;

    public static MessageDto fromEntity(MessageEntity messageEntity){
        return MessageDto.builder()
                .id(messageEntity.getId().toString())
                .sender(messageEntity.getMember().getMobile())
                .message(messageEntity.getMessage())
                .createdAt(messageEntity.getCreatedAt().toString())
                .build();
    }
}
