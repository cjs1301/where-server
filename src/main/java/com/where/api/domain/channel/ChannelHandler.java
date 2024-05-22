package com.where.api.domain.channel;

import com.where.api.domain.channel.dto.ChannelDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

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
                .flatMap(body -> channelService.saveChannelEntity(mapper.channelCreateToChannelEntity(body)))
                .flatMap(body -> created);
    }
}
