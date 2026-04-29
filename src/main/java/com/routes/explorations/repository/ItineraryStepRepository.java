package com.routes.explorations.repository;

import com.routes.explorations.entity.ItineraryStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItineraryStepRepository extends JpaRepository<ItineraryStep, Long> {
    List<ItineraryStep> findByItineraryPlanId(Long itineraryPlanId);
    List<ItineraryStep> findByItineraryPlanIdOrderByStepNumber(Long itineraryPlanId);
    List<ItineraryStep> findByItineraryPlanIdOrderByStepNumberAsc(Long itineraryPlanId);
}

