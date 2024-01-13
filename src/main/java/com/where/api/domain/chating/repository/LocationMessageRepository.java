package com.where.api.domain.chating.repository;

import com.where.api.domain.chating.entity.LocationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationMessageRepository extends JpaRepository<LocationMessage, Long> {
}
