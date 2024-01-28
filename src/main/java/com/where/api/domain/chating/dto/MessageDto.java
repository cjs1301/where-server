package com.where.api.domain.chating.dto;

import com.where.api.domain.chating.entity.MessageEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageDto {
    Long id;
    String channelId;
    String sender;
    String message;
    OffsetDateTime createdAt;

    public static MessageDto fromEntity(MessageEntity messageEntity){
        return MessageDto.builder()
                .id(messageEntity.getId())
                .sender(messageEntity.getMember().getMobile())
                .message(messageEntity.getMessage())
                .createdAt(messageEntity.getCreatedAt())
                .build();
    }
}
