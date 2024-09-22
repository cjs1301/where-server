package org.where.moduleapi.api.service.channel.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.where.modulecore.domain.channel.ChannelMembershipEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChannelDto {

    UUID channelId;

    UUID membershipId;

    String channelName;

    String lastMessage;

    LocalDateTime lastMessageTime;

    public static ChannelDto fromEntity(ChannelMembershipEntity channelMembership){
        return ChannelDto.builder()
                .membershipId(channelMembership.getId())
                .channelId(channelMembership.getChannel().getId())
                .lastMessage(channelMembership.getChannel().getLastMessage())
                .lastMessageTime(channelMembership.getChannel().getLastMessageTime())
                .build();
    }

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CreateOneToOneChannel {
        String channelName;
        Long targetMemberId;
    }
}
