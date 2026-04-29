package com.routes.explorations.repository;

import com.routes.explorations.entity.Destinations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DestinationsRepository extends JpaRepository<Destinations, Long> {
    Optional<Destinations> findBySlug(String slug);
    List<Destinations> findByRegionId(Long regionId);
    List<Destinations> findByIsFeaturedTrue();
    List<Destinations> findByRegionIdOrderByName(Long regionId);
}

