package com.where.backend.api.service.channel;

import com.where.backend.api.service.channel.dto.ChannelDto;
import com.where.backend.api.service.channel.dto.ChatMessageDto;
import com.where.backend.domain.channel.entity.ChannelEntity;
import com.where.backend.domain.channel.entity.ChatMessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChannelMapper {
    ChannelEntity channelCreateToChannelEntity(ChannelDto.Create requestBody);

    List<ChannelDto.Response> channelEntityToResponse(List<ChannelEntity> channels);

    List<ChatMessageDto.Response> chatMessageEntityToResponse(List<ChatMessageEntity> messageEntities);

}
