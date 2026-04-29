package com.routes.explorations.controller;

import com.routes.explorations.entity.ItineraryStep;
import com.routes.explorations.repository.ItineraryStepRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itinerary-steps")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class ItineraryStepController {

    private final ItineraryStepRepository itineraryStepRepository;


    @GetMapping
    public List<ItineraryStep> getAllItinerarySteps() {
        return itineraryStepRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItineraryStep> getItineraryStepById(@PathVariable Long id) {
        Optional<ItineraryStep> step = itineraryStepRepository.findById(id);
        return step.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/plan/{planId}")
    public List<ItineraryStep> getStepsByPlan(@PathVariable Long planId) {
        return itineraryStepRepository.findByItineraryPlanIdOrderByStepNumber(planId);
    }

    @PostMapping
    public ItineraryStep createItineraryStep(@RequestBody ItineraryStep step) {
        return itineraryStepRepository.save(step);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItineraryStep> updateItineraryStep(
            @PathVariable Long id, @RequestBody ItineraryStep stepDetails) {
        Optional<ItineraryStep> step = itineraryStepRepository.findById(id);
        if (step.isPresent()) {
            ItineraryStep existing = step.get();
            existing.setItineraryPlan(stepDetails.getItineraryPlan());
            existing.setStepNumber(stepDetails.getStepNumber());
            existing.setTimeOfDay(stepDetails.getTimeOfDay());
            existing.setDescription(stepDetails.getDescription());
            return ResponseEntity.ok(itineraryStepRepository.save(existing));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItineraryStep(@PathVariable Long id) {
        if (itineraryStepRepository.existsById(id)) {
            itineraryStepRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

