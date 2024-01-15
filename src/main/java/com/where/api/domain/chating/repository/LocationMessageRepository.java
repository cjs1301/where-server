package com.where.api.domain.chating.repository;

import com.where.api.domain.chating.entity.LocationMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationMessageRepository extends JpaRepository<LocationMessageEntity, Long> {
}
