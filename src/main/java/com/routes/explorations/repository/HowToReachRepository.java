package com.routes.explorations.repository;

import com.routes.explorations.entity.HowToReach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HowToReachRepository extends JpaRepository<HowToReach, Long> {
    List<HowToReach> findByDestinationId(Long destinationId);
    List<HowToReach> findByDestinationIdOrderBySortOrder(Long destinationId);
}

