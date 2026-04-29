package com.routes.explorations.controller;

import com.routes.explorations.entity.DestinationCategory;
import com.routes.explorations.entity.DestinationCategoryId;
import com.routes.explorations.repository.DestinationCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/destination-categories")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class DestinationCategoryController {

    private final DestinationCategoryRepository destinationCategoryRepository;

    @GetMapping
    public List<DestinationCategory> getAllDestinationCategories() {
        log.info("Fetching all destination categories");
        List<DestinationCategory> categories = destinationCategoryRepository.findAll();
        log.info("Retrieved {} destination categories", categories.size());
        return categories;
    }

    @GetMapping("/{destinationId}/{categoryId}")
    public ResponseEntity<DestinationCategory> getDestinationCategoryById(
            @PathVariable Long destinationId, @PathVariable Long categoryId) {
        log.info("Fetching destination category: destination={}, category={}", destinationId, categoryId);
        DestinationCategoryId id = new DestinationCategoryId(destinationId, categoryId);
        Optional<DestinationCategory> dc = destinationCategoryRepository.findById(id);
        if (dc.isPresent()) {
            log.info("Destination category found");
        } else {
            log.warn("Destination category not found: destination={}, category={}", destinationId, categoryId);
        }
        return dc.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/destination/{destinationId}")
    public List<DestinationCategory> getCategoriesByDestination(@PathVariable Long destinationId) {
        log.info("Fetching categories for destination: {}", destinationId);
        List<DestinationCategory> categories = destinationCategoryRepository.findByDestinationId(destinationId);
        log.info("Retrieved {} categories for destination: {}", categories.size(), destinationId);
        return categories;
    }

    @GetMapping("/category/{categoryId}")
    public List<DestinationCategory> getDestinationsByCategory(@PathVariable Long categoryId) {
        log.info("Fetching destinations for category: {}", categoryId);
        List<DestinationCategory> destinations = destinationCategoryRepository.findByCategoryId(categoryId);
        log.info("Retrieved {} destinations for category: {}", destinations.size(), categoryId);
        return destinations;
    }

    @PostMapping
    public ResponseEntity<DestinationCategory> createDestinationCategory(@RequestBody DestinationCategory dc) {
        log.info("Creating destination category");
        if (dc.getId() == null) {
            log.error("Composite ID (destinationId, categoryId) is required");
            return ResponseEntity.badRequest().build();
        }
        DestinationCategory saved = destinationCategoryRepository.save(dc);
        log.info("Destination category created successfully");
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{destinationId}/{categoryId}")
    public ResponseEntity<DestinationCategory> updateDestinationCategory(
            @PathVariable Long destinationId, @PathVariable Long categoryId,
            @RequestBody DestinationCategory dcDetails) {
        log.info("Updating destination category: destination={}, category={}", destinationId, categoryId);
        DestinationCategoryId id = new DestinationCategoryId(destinationId, categoryId);
        Optional<DestinationCategory> dc = destinationCategoryRepository.findById(id);
        if (dc.isPresent()) {
            DestinationCategory existing = dc.get();
            existing.setIsPrimary(dcDetails.getIsPrimary());
            DestinationCategory updated = destinationCategoryRepository.save(existing);
            log.info("Destination category updated successfully");
            return ResponseEntity.ok(updated);
        }
        log.warn("Destination category not found for update: destination={}, category={}", destinationId, categoryId);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{destinationId}/{categoryId}")
    public ResponseEntity<Void> deleteDestinationCategory(@PathVariable Long destinationId, @PathVariable Long categoryId) {
        log.info("Deleting destination category: destination={}, category={}", destinationId, categoryId);
        DestinationCategoryId id = new DestinationCategoryId(destinationId, categoryId);
        if (destinationCategoryRepository.existsById(id)) {
            destinationCategoryRepository.deleteById(id);
            log.info("Destination category deleted successfully");
            return ResponseEntity.noContent().build();
        }
        log.warn("Destination category not found for deletion: destination={}, category={}", destinationId, categoryId);
        return ResponseEntity.notFound().build();
    }
}

