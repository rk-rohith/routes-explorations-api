package com.routes.explorations.controller;

import com.routes.explorations.entity.HowToReach;
import com.routes.explorations.repository.HowToReachRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/how-to-reach")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class HowToReachController {

    private final HowToReachRepository howToReachRepository;


    @GetMapping
    public List<HowToReach> getAllHowToReach() {
        return howToReachRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HowToReach> getHowToReachById(@PathVariable Long id) {
        Optional<HowToReach> howToReach = howToReachRepository.findById(id);
        return howToReach.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/destination/{destinationId}")
    public List<HowToReach> getHowToReachByDestination(@PathVariable Long destinationId) {
        return howToReachRepository.findByDestinationIdOrderBySortOrder(destinationId);
    }

    @PostMapping
    public HowToReach createHowToReach(@RequestBody HowToReach howToReach) {
        return howToReachRepository.save(howToReach);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HowToReach> updateHowToReach(@PathVariable Long id, @RequestBody HowToReach howToReachDetails) {
        Optional<HowToReach> howToReach = howToReachRepository.findById(id);
        if (howToReach.isPresent()) {
            HowToReach htr = howToReach.get();
            htr.setDestination(howToReachDetails.getDestination());
            htr.setMode(howToReachDetails.getMode());
            htr.setDescription(howToReachDetails.getDescription());
            htr.setSortOrder(howToReachDetails.getSortOrder());
            return ResponseEntity.ok(howToReachRepository.save(htr));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHowToReach(@PathVariable Long id) {
        if (howToReachRepository.existsById(id)) {
            howToReachRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

