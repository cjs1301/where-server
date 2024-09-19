package org.where.modulecore.domain.channel;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("ONE_TO_ONE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneToOneChannelEntity extends ChannelEntity {
    @Builder
    public OneToOneChannelEntity(UUID id, String lastMessage, LocalDateTime lastMessageTime) {
        super(id, lastMessage, lastMessageTime);
    }
}
