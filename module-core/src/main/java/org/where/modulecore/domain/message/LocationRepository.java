package org.where.modulecore.domain.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    @Transactional
    @Modifying
    @Query("delete from LocationEntity l where l.member.id = ?1")
    void deleteAllByMemberId(Long id);
}
