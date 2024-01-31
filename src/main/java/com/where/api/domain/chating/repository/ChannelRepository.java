package com.where.api.domain.chating.repository;

import com.where.api.domain.chating.entity.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChannelRepository extends JpaRepository<ChannelEntity, UUID> {

}
