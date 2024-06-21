package com.where.backend.api.router.channel;

import com.where.backend.api.service.channel.dto.ChannelDto;
import com.where.backend.api.service.channel.ChannelService;
import com.where.backend.api.service.channel.ChannelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChannelHandler {
    private final ChannelMapper mapper;
    private final ChannelService channelService;
    private final ChannelValidator channelValidator;

    public Mono<ServerResponse> createChannel(ServerRequest req) {
        return req.bodyToMono(ChannelDto.Create.class)
                .doOnNext(body -> channelValidator.validate(body))
                .flatMap(body -> channelService.createChannel(mapper.channelCreateToChannelEntity(body)))
                .flatMap(channel -> ServerResponse
                        .created(URI.create("/channels/" + channel.getId()))
                        .build());
    }
    public Mono<ServerResponse> deleteChannel(ServerRequest req) {
        UUID channelId = UUID.fromString(req.pathVariable("channelId"));
        return channelService.deleteChannel(channelId)
                .then(ServerResponse.ok().build());
    }
    public Mono<ServerResponse> deleteFollowChannel(ServerRequest req) {
        UUID followId = UUID.fromString(req.pathVariable("followId"));
        return channelService.deleteFollowChannel(followId)
                .then(ServerResponse.ok().build());
    }
    public Mono<ServerResponse> getFollowChannelList(ServerRequest req) {
        long memberId = 1L;
        return channelService.findFollowChannelList(memberId).flatMap(
                channels -> ServerResponse.ok().bodyValue(mapper.channelEntityToResponse(channels))
        );
    }
    public Mono<ServerResponse> getChannelMessageList(ServerRequest req) {
        UUID channelId = UUID.fromString(req.pathVariable("channelId"));
        return channelService.findChannelMessageList(channelId).flatMap(messageEntities ->
            ServerResponse.ok().bodyValue(mapper.chatMessageEntityToResponse(messageEntities))
        );
    }
    public Mono<ServerResponse> followingChannel(ServerRequest req) {
        UUID channelId = UUID.fromString(req.pathVariable("channelId"));
        long memberId = 1L;
        return channelService.createFollowChannel(channelId,memberId)
    }

}
