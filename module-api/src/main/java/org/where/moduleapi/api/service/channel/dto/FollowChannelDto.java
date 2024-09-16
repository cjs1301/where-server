package org.where.moduleapi.api.service.channel.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.where.modulecore.domain.channel.FollowChannelEntity;

import java.time.OffsetDateTime;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FollowChannelDto {
    String followChannelId;

    String channelId;

    String channelName;

    OffsetDateTime createdAt;

    public static FollowChannelDto fromEntity(FollowChannelEntity followChannelEntity){
        return FollowChannelDto.builder()
                .followChannelId(followChannelEntity.getId().toString())
                .channelId(followChannelEntity.getChannel().getId().toString())
                .channelName(followChannelEntity.getChannel().getName())
                .createdAt(followChannelEntity.getChannel().getCreatedAt())
                .build();
    }
}
