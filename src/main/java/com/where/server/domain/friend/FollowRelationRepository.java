package com.where.server.domain.friend;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FollowRelationRepository extends JpaRepository<FollowRelationEntity, Long>, FollowRelationRepoCustom {

}
