package com.where.backend.api.router.channel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Slf4j
@Configuration("channelRouter")
public class ChannelRouter {

    @Bean
    public RouterFunction<?> routerChannel(ChannelHandler handler){
        return route()
                .POST("/channels",handler::createChannel)
                .DELETE("/channels/{channelId}",handler::deleteChannel)
                .DELETE("/follow-channel/{followId}",handler::deleteFollowChannel)
                .GET("/follow-channel",handler::getFollowChannelList)
                .GET("/channels/{channelId}/messages",handler::getChannelMessageList)
                .POST("/channels/{channelId}/following",handler::followingChannel)
                .build();
    }

//    @PostMapping
//    public ResponseEntity<Mono<ChannelDto.Response>> createChannel(@AuthenticationPrincipal CustomUserDetails user, @RequestBody ChannelDto.Create body){
//        return ResponseEntity.created(channelService.createChannel(body));
//    }
//    @DeleteMapping("/{channelId}/follows/{followId}")
//    public ResponseEntity<String> deleteFollowChannelAndChannel(@PathVariable UUID channelId,@PathVariable UUID followId){
//        channelService.deleteFollowChannel(channelId,followId);
//        return ResponseEntity.ok("삭제");
//    }
//    @GetMapping("follow")
//    public ResponseEntity<List<FollowChannelDto>> getFollowChannelList(@AuthenticationPrincipal CustomUserDetails user){
//        return ResponseEntity.ok(channelService.getFollowChannelList(user.getId()));
//    }
//    @GetMapping("/{channelId}/messages")
//    public ResponseEntity<List<MessageDto>> getChannelMessageList(@PathVariable UUID channelId){
//        return ResponseEntity.ok(channelService.getChannelMessageList(channelId));
//    }
//
//    @PostMapping("/{channelId}/follow")
//    public ResponseEntity<FollowChannelDto> createFollowChannel(@PathVariable UUID channelId,@AuthenticationPrincipal CustomUserDetails user){
//        return ResponseEntity.ok(channelService.createFollowChannel(channelId,user.getId()));
//    }

}
