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
    @Column(name = "member1_id")
    private Long member1Id;

    @Column(name = "member2_id")
    private Long member2Id;

    @Builder
    public OneToOneChannelEntity(UUID id, String lastMessage, LocalDateTime lastMessageTime, Long member1Id, Long member2Id) {
        super(id, lastMessage, lastMessageTime, ChannelType.ONE_TO_ONE);
        this.member1Id = member1Id;
        this.member2Id = member2Id;
    }
}
