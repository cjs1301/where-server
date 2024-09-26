package org.where.modulecore.domain.message;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;
import org.where.modulecore.domain.TimeStamped;
import org.where.modulecore.domain.channel.ChannelEntity;
import org.where.modulecore.domain.member.MemberEntity;

import java.util.UUID;

@Entity
@Table(name = "message")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageEntity extends TimeStamped {
    @Id
    @UuidGenerator
    @Column(name = "message_id", nullable = false)
    UUID id;

    String message;

    @Column(name = "is_read", nullable = false)
    Boolean isRead;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "channel_id")
    ChannelEntity channel;
    @ManyToOne
    @JoinColumn(name = "member_id")
    MemberEntity member;

    public MessageEntity updateToRead(){
        this.isRead = true;
        return this;
    }
}
