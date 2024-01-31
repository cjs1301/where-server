package com.where.api.domain.chating.dto;

import com.where.api.domain.chating.entity.MessageEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageDto {
    String id;
    String channelId;
    String sender;
    String message;
    OffsetDateTime createdAt;

    public static MessageDto fromEntity(MessageEntity messageEntity){
        return MessageDto.builder()
                .id(messageEntity.getId().toString())
                .sender(messageEntity.getMember().getMobile())
                .message(messageEntity.getMessage())
                .createdAt(messageEntity.getCreatedAt())
                .build();
    }
}
