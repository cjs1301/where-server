package com.where.api.domain.chating.repository;

import com.where.api.domain.chating.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
}
