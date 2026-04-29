package com.routes.explorations.controller;

import com.routes.explorations.entity.Region;
import com.routes.explorations.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/regions")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class RegionController {

    private final RegionRepository regionRepository;


    @GetMapping
    public List<Region> getAllRegions() {
        log.info("Fetching all regions");
        List<Region> regions = regionRepository.findAll();
        log.info("Retrieved {} regions", regions.size());
        return regions;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Region> getRegionById(@PathVariable Long id) {
        log.info("Fetching region with ID: {}", id);
        Optional<Region> region = regionRepository.findById(id);
        if (region.isPresent()) {
            log.info("Region found with ID: {}", id);
        } else {
            log.warn("Region not found with ID: {}", id);
        }
        return region.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<Region> getRegionBySlug(@PathVariable String slug) {
        log.info("Fetching region by slug: {}", slug);
        Optional<Region> region = regionRepository.findBySlug(slug);
        if (region.isPresent()) {
            log.info("Region found with slug: {}", slug);
        } else {
            log.warn("Region not found with slug: {}", slug);
        }
        return region.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Region> saveRegion(@RequestBody Region region) {
        log.info("Creating new region: {}", region.getName());
        Region savedRegion = regionRepository.save(region);
        log.info("Region created successfully with ID: {}", savedRegion.getId());
        return ResponseEntity.ok(savedRegion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Region> updateRegion(
            @PathVariable Long id,
            @RequestBody Region regionDetails) {
        log.info("Updating region with ID: {}", id);
        Optional<Region> region = regionRepository.findById(id);

        if (region.isEmpty()) {
            log.warn("Region not found for update with ID: {}", id);
            return ResponseEntity.notFound().build();
        }

        Region existingRegion = region.get();
        existingRegion.setName(regionDetails.getName());
        existingRegion.setSlug(regionDetails.getSlug());
        existingRegion.setImageUrl(regionDetails.getImageUrl());
        existingRegion.setStatus(regionDetails.getStatus());

        Region updatedRegion = regionRepository.save(existingRegion);
        log.info("Region updated successfully with ID: {}", id);
        return ResponseEntity.ok(updatedRegion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
        log.info("Deleting region with ID: {}", id);
        Optional<Region> region = regionRepository.findById(id);

        if (region.isEmpty()) {
            log.warn("Region not found for deletion with ID: {}", id);
            return ResponseEntity.notFound().build();
        }

        regionRepository.deleteById(id);
        log.info("Region deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}

