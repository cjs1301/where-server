package org.where.moduleapi.api.service.channel.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.where.modulecore.domain.channel.ChannelEntity;
import org.where.modulecore.domain.channel.ChannelMembershipEntity;
import org.where.modulecore.domain.channel.ChannelType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChannelDto {

    UUID channelId;

    UUID membershipId;

    ChannelType channelType;

    String channelName;

    String lastMessage;

    LocalDateTime lastMessageTime;

    Integer unreadCount;

    public static ChannelDto fromEntity(ChannelMembershipEntity channelMembership){
        return ChannelDto.builder()
                .channelName(channelMembership.getChannel().getName())
                .membershipId(channelMembership.getId())
                .channelId(channelMembership.getChannel().getId())
                .channelType(channelMembership.getChannelType())
                .lastMessage(channelMembership.getChannel().getLastMessage())
                .lastMessageTime(channelMembership.getChannel().getLastMessageTime())
                .unreadCount(channelMembership.getChannel().getUnreadCount())
                .build();
    }

    public static ChannelDto fromEntity(ChannelEntity channel){
        return ChannelDto.builder()
                .channelName(channel.getName())
                .channelId(channel.getId())
                .channelType(channel.getChannelType())
                .lastMessage(channel.getLastMessage())
                .lastMessageTime(channel.getLastMessageTime())
                .unreadCount(channel.getUnreadCount())
                .build();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CreateOneToOneChannel {
        String channelName;
        Long targetMemberId;
    }
}
