package com.routes.explorations.controller;

import com.routes.explorations.entity.TagDestination;
import com.routes.explorations.entity.TagDestinationId;
import com.routes.explorations.repository.TagDestinationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tag-destinations")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class TagDestinationController {

    private final TagDestinationRepository tagDestinationRepository;

    @GetMapping
    public List<TagDestination> getAllTagDestinations() {
        log.info("Fetching all tag destinations");
        List<TagDestination> destinations = tagDestinationRepository.findAll();
        log.info("Retrieved {} tag destinations", destinations.size());
        return destinations;
    }

    @GetMapping("/{tagId}/{destinationId}")
    public ResponseEntity<TagDestination> getTagDestinationById(
            @PathVariable Long tagId, @PathVariable Long destinationId) {
        log.info("Fetching tag destination: tag={}, destination={}", tagId, destinationId);
        TagDestinationId id = new TagDestinationId(tagId, destinationId);
        Optional<TagDestination> td = tagDestinationRepository.findById(id);
        if (td.isPresent()) {
            log.info("Tag destination found");
        } else {
            log.warn("Tag destination not found: tag={}, destination={}", tagId, destinationId);
        }
        return td.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/tag/{tagId}")
    public List<TagDestination> getDestinationsByTag(@PathVariable Long tagId) {
        log.info("Fetching destinations for tag: {}", tagId);
        List<TagDestination> destinations = tagDestinationRepository.findByTagId(tagId);
        log.info("Retrieved {} destinations for tag: {}", destinations.size(), tagId);
        return destinations;
    }

    @GetMapping("/destination/{destinationId}")
    public List<TagDestination> getTagsByDestination(@PathVariable Long destinationId) {
        log.info("Fetching tags for destination: {}", destinationId);
        List<TagDestination> tags = tagDestinationRepository.findByDestinationId(destinationId);
        log.info("Retrieved {} tags for destination: {}", tags.size(), destinationId);
        return tags;
    }

    @PostMapping
    public ResponseEntity<TagDestination> createTagDestination(@RequestBody TagDestination td) {
        log.info("Creating tag destination");
        if (td.getId() == null) {
            log.error("Composite ID (tagId, destinationId) is required");
            return ResponseEntity.badRequest().build();
        }
        TagDestination saved = tagDestinationRepository.save(td);
        log.info("Tag destination created successfully");
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{tagId}/{destinationId}")
    public ResponseEntity<TagDestination> updateTagDestination(
            @PathVariable Long tagId, @PathVariable Long destinationId,
            @RequestBody TagDestination tdDetails) {
        log.info("Updating tag destination: tag={}, destination={}", tagId, destinationId);
        TagDestinationId id = new TagDestinationId(tagId, destinationId);
        Optional<TagDestination> td = tagDestinationRepository.findById(id);
        if (td.isPresent()) {
            TagDestination existing = td.get();
            TagDestination updated = tagDestinationRepository.save(existing);
            log.info("Tag destination updated successfully");
            return ResponseEntity.ok(updated);
        }
        log.warn("Tag destination not found for update: tag={}, destination={}", tagId, destinationId);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{tagId}/{destinationId}")
    public ResponseEntity<Void> deleteTagDestination(@PathVariable Long tagId, @PathVariable Long destinationId) {
        log.info("Deleting tag destination: tag={}, destination={}", tagId, destinationId);
        TagDestinationId id = new TagDestinationId(tagId, destinationId);
        if (tagDestinationRepository.existsById(id)) {
            tagDestinationRepository.deleteById(id);
            log.info("Tag destination deleted successfully");
            return ResponseEntity.noContent().build();
        }
        log.warn("Tag destination not found for deletion: tag={}, destination={}", tagId, destinationId);
        return ResponseEntity.notFound().build();
    }
}


