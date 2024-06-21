package com.where.backend.api.service.channel.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;
import java.util.UUID;

public class ChannelDto {

    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Create{
        @NotBlank
        String channelName;
    }

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Response{
        UUID channelId;
        String channelName;
        OffsetDateTime createdAt;
        OffsetDateTime updatedAt;
    }
}
