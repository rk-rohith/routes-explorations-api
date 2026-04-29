package com.routes.explorations.controller;

import com.routes.explorations.entity.ItineraryPlan;
import com.routes.explorations.repository.ItineraryPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itinerary-plans")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class ItineraryPlanController {

    private final ItineraryPlanRepository itineraryPlanRepository;

    @GetMapping
    public List<ItineraryPlan> getAllItineraryPlans() {
        return itineraryPlanRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItineraryPlan> getItineraryPlanById(@PathVariable Long id) {
        Optional<ItineraryPlan> plan = itineraryPlanRepository.findById(id);
        return plan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/destination/{destinationId}")
    public List<ItineraryPlan> getItineraryPlansByDestination(@PathVariable Long destinationId) {
        return itineraryPlanRepository.findByDestinationIdOrderBySortOrder(destinationId);
    }

    @PostMapping
    public ItineraryPlan createItineraryPlan(@RequestBody ItineraryPlan plan) {
        return itineraryPlanRepository.save(plan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItineraryPlan> updateItineraryPlan(@PathVariable Long id, @RequestBody ItineraryPlan planDetails) {
        Optional<ItineraryPlan> plan = itineraryPlanRepository.findById(id);
        if (plan.isPresent()) {
            ItineraryPlan p = plan.get();
            p.setDestination(planDetails.getDestination());
            p.setPlanLabel(planDetails.getPlanLabel());
            p.setDurationDays(planDetails.getDurationDays());
            p.setSortOrder(planDetails.getSortOrder());
            return ResponseEntity.ok(itineraryPlanRepository.save(p));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItineraryPlan(@PathVariable Long id) {
        if (itineraryPlanRepository.existsById(id)) {
            itineraryPlanRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

