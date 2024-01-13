package com.where.api.domain.chating.entity;

import com.where.api.core.common.TimeStamped;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_message")
@NoArgsConstructor
public class ChatMessage extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(length = 500)
    String message;
}
