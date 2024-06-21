package com.where.backend.domain;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.OffsetDateTime;


public class TimeStamped {
    @CreatedDate
    @Column("created_at")
    OffsetDateTime createdAt;
    @LastModifiedDate
    @Column("updated_at")
    OffsetDateTime updatedAt;
}
