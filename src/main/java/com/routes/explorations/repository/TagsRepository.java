package com.routes.explorations.repository;

import com.routes.explorations.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TagsRepository extends JpaRepository<Tags, Long> {
    Optional<Tags> findBySlug(String slug);
    List<Tags> findByRegionId(Long regionId);
    List<Tags> findByRegionIdOrderBySortOrder(Long regionId);
}

