package org.where.modulecore.domain.channel;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@DiscriminatorValue("GROUP")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupChannelEntity extends ChannelEntity {
    @Column(name = "name")
    private String name;

    @Builder
    public GroupChannelEntity(UUID id, String name, String lastMessage, LocalDateTime lastMessageTime) {
        super(id, lastMessage, lastMessageTime);
        this.name = name;
    }
}
