package com.where.backend.api.service.channel.dto;

import com.where.backend.domain.channel.entity.FollowChannelEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

public class FollowChannelDto {
    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Response {
        String followChannelId;

        String channelId;

        String channelName;

        OffsetDateTime createdAt;
    }
    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Create{
        @NotBlank
        String channelName;
        @NotBlank
        String creatorMobile;
    }

    public static FollowChannelDto fromEntity(FollowChannelEntity followChannelEntity){
        return FollowChannelDto.builder()
                .followChannelId(followChannelEntity.getId().toString())
                .channelId(followChannelEntity.getChannel().getId().toString())
                .channelName(followChannelEntity.getChannel().getName())
                .createdAt(followChannelEntity.getChannel().getCreatedAt())
                .build();
    }
}
