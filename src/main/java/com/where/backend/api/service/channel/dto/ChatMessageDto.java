package com.where.backend.api.service.channel.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

public class ChatMessageDto {

    @Getter
    @Builder
    @ToString
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Response {
        String id;
        String channelId;
        String sender;
        String message;
        String createdAt;

//        public static com.where.api.domain.channel.dto.ChatMessageDto fromEntity(MessageEntity messageEntity){
//            return com.where.api.domain.channel.dto.ChatMessageDto.builder()
//                    .id(messageEntity.getId().toString())
//                    .sender(messageEntity.getMember().getMobile())
//                    .message(messageEntity.getMessage())
//                    .createdAt(messageEntity.getCreatedAt().toString())
//                    .build();
//        }
    }
    @Getter
    @Builder
    @ToString
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class LocationMessageDto {
        String type;
        String sender;
        String channelId;
        CoordinateDto coordinates;
    }
}
