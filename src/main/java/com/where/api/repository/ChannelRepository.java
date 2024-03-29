package com.where.api.repository;

import com.where.api.domain.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChannelRepository extends JpaRepository<ChannelEntity, UUID> {

}
