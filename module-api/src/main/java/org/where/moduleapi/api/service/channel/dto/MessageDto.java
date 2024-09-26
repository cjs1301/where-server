package org.where.moduleapi.api.service.channel.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.where.modulecore.domain.message.MessageEntity;

@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageDto {
    String id;
    String channelId;
    String sender;
    String message;
    Boolean isRead;
    String createdAt;

    public static MessageDto fromEntity(MessageEntity messageEntity){
        return MessageDto.builder()
                .id(messageEntity.getId().toString())
                .sender(messageEntity.getMember().getPhoneNumber())
                .message(messageEntity.getMessage())
                .isRead(messageEntity.getIsRead())
                .createdAt(messageEntity.getCreatedAt().toString())
                .build();
    }
}
