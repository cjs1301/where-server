package org.where.modulewebsocket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.where.modulecore.domain.channel.ChannelMembershipRepository;

import java.util.UUID;

@Service
public class WebSocketConnectionService {

    private final ChannelMembershipRepository channelMembershipRepository;

    public WebSocketConnectionService(ChannelMembershipRepository channelMembershipRepository) {
        this.channelMembershipRepository = channelMembershipRepository;
    }

    @Transactional
    public void handleSubscribe(String connectionId, Long memberId, UUID channelId) {
        channelMembershipRepository
                .findByChannelIdAndMemberId(channelId, memberId)
                .ifPresentOrElse(
                        membership -> {
                            membership.setConnectionId(connectionId);
                            channelMembershipRepository.save(membership);
                        },
                        () -> {
                            throw new RuntimeException("Channel membership not found for member " + memberId + " and channel " + channelId);
                        }
                );
    }

    @Transactional
    public void handleDisconnect(String connectionId) {
        channelMembershipRepository
                .findByConnectionId(connectionId)
                .ifPresent(membership -> {
                    membership.setConnectionId(null);
                    channelMembershipRepository.save(membership);
                });
    }

}
