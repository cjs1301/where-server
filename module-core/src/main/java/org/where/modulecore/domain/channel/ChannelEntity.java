package org.where.modulecore.domain.channel;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;
import org.where.modulecore.domain.TimeStamped;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "channel")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "channel_type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class ChannelEntity extends TimeStamped {

    @Id
    @UuidGenerator
    @Column(name = "channel_id", nullable = false)
    UUID id;

    @Setter
    @Column(name = "name")
    private String name;

    @Column(name = "last_message")
    String lastMessage;

    @Column(name = "last_message_time")
    LocalDateTime lastMessageTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel_type", insertable = false, updatable = false)
    ChannelType channelType;

    @Column(name = "unread_count")
    Integer unreadCount = 0;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<ChannelMembershipEntity> memberships = new HashSet<>();

    ChannelEntity(UUID id, String lastMessage, LocalDateTime lastMessageTime,ChannelType channelType) {
        this.id = id;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
        this.channelType = channelType;
    }

}
