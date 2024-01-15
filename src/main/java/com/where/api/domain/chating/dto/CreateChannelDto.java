package com.where.api.domain.chating.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateChannelDto {
    private String channelName;
    private String memberMobileNumber;
}
