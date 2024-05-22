package com.where.api.domain.channel;

import com.where.api.domain.channel.dto.ChannelDto;
import com.where.api.domain.channel.entity.ChannelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChannelMapper {
    ChannelEntity channelCreateToChannelEntity(ChannelDto.Create requestBody);
}
