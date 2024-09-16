package org.where.moduleapi.api.service.channel.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateChannelDto {
    String channelName;
    String memberPhoneNumber;
}
