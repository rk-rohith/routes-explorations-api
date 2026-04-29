package com.routes.explorations.repository;

import com.routes.explorations.entity.ItineraryPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItineraryPlanRepository extends JpaRepository<ItineraryPlan, Long> {
    List<ItineraryPlan> findByDestinationId(Long destinationId);
    List<ItineraryPlan> findByDestinationIdOrderBySortOrder(Long destinationId);
}

