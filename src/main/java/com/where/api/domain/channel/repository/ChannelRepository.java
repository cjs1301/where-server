package com.where.api.domain.channel.repository;

import com.where.api.domain.channel.entity.ChannelEntity;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChannelRepository extends R2dbcRepository<ChannelEntity, UUID> {
}
