package com.routes.explorations.controller;

import com.routes.explorations.entity.Destinations;
import com.routes.explorations.repository.DestinationsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/destinations")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class DestinationController {

    private final DestinationsRepository destinationsRepository;


    @GetMapping
    public List<Destinations> getAllDestinations() {
        return destinationsRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destinations> getDestinationById(@PathVariable Long id) {
        Optional<Destinations> destination = destinationsRepository.findById(id);
        return destination.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<Destinations> getDestinationBySlug(@PathVariable String slug) {
        Optional<Destinations> destination = destinationsRepository.findBySlug(slug);
        return destination.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/region/{regionId}")
    public List<Destinations> getDestinationsByRegion(@PathVariable Long regionId) {
        return destinationsRepository.findByRegionIdOrderByName(regionId);
    }

    @GetMapping("/featured")
    public List<Destinations> getFeaturedDestinations() {
        return destinationsRepository.findByIsFeaturedTrue();
    }

    @PostMapping
    public ResponseEntity<Destinations> saveDestination(@RequestBody Destinations destination) {
        Destinations savedDestination = destinationsRepository.save(destination);
        return ResponseEntity.ok(savedDestination);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Destinations> updateDestination(
            @PathVariable Long id,
            @RequestBody Destinations destinationDetails) {
        Optional<Destinations> destination = destinationsRepository.findById(id);

        if (destination.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Destinations existingDestination = destination.get();
        existingDestination.setName(destinationDetails.getName());
        existingDestination.setSlug(destinationDetails.getSlug());
        existingDestination.setTagline(destinationDetails.getTagline());
        existingDestination.setOverview(destinationDetails.getOverview());
        existingDestination.setShortDescription(destinationDetails.getShortDescription());
        existingDestination.setImageUrl(destinationDetails.getImageUrl());
        existingDestination.setDistanceKm(destinationDetails.getDistanceKm());
        existingDestination.setTravelTime(destinationDetails.getTravelTime());
        existingDestination.setBestTimeToVisit(destinationDetails.getBestTimeToVisit());
        existingDestination.setIsFeatured(destinationDetails.getIsFeatured());
        existingDestination.setTravelTips(destinationDetails.getTravelTips());
        existingDestination.setThingsToDo(destinationDetails.getThingsToDo());
        existingDestination.setLatitude(destinationDetails.getLatitude());
        existingDestination.setLongitude(destinationDetails.getLongitude());
        existingDestination.setLocationName(destinationDetails.getLocationName());
        existingDestination.setMapAvailable(destinationDetails.getMapAvailable());

        if (destinationDetails.getRegion() != null) {
            existingDestination.setRegion(destinationDetails.getRegion());
        }

        Destinations updatedDestination = destinationsRepository.save(existingDestination);
        return ResponseEntity.ok(updatedDestination);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        Optional<Destinations> destination = destinationsRepository.findById(id);

        if (destination.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        destinationsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

