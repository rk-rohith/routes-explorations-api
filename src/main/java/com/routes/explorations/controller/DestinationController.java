package com.routes.explorations.controller;

import com.routes.explorations.entity.Destinations;
import com.routes.explorations.entity.Region;
import com.routes.explorations.entity.Category;
import com.routes.explorations.repository.DestinationsRepository;
import com.routes.explorations.repository.RegionRepository;
import com.routes.explorations.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/destinations")
@Tag(name = "Destination Management", description = "APIs for managing destinations")
public class DestinationController {

    @Autowired
    private DestinationsRepository destinationsRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    @Operation(summary = "Get all destinations", description = "Retrieve a list of all destinations")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all destinations")
    public List<Destinations> getAllDestinations() {
        return destinationsRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get destination by ID", description = "Retrieve a specific destination by its ID")
    @ApiResponse(responseCode = "200", description = "Destination found")
    @ApiResponse(responseCode = "404", description = "Destination not found")
    public ResponseEntity<Destinations> getDestinationById(@PathVariable Long id) {
        Optional<Destinations> destination = destinationsRepository.findById(id);
        return destination.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new destination", description = "Save a new destination to the database")
    @ApiResponse(responseCode = "200", description = "Destination created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid region or category ID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Destinations.class),
                    examples = @ExampleObject(value = "{\"name\": \"Tokyo Tower\", \"description\": \"Iconic tower in Japan\", \"region\": {\"id\": 1}, \"category\": {\"id\": 1}}")))
    public ResponseEntity<Destinations> saveDestination(
            @RequestBody Destinations destination) {
        // Validate that region and category exist
        if (destination.getRegion() == null || destination.getRegion().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (destination.getCategory() == null || destination.getCategory().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Region> region = regionRepository.findById(destination.getRegion().getId());
        Optional<Category> category = categoryRepository.findById(destination.getCategory().getId());

        if (region.isEmpty() || category.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        destination.setRegion(region.get());
        destination.setCategory(category.get());

        Destinations savedDestination = destinationsRepository.save(destination);
        return ResponseEntity.ok(savedDestination);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a destination", description = "Update an existing destination by its ID")
    @ApiResponse(responseCode = "200", description = "Destination updated successfully")
    @ApiResponse(responseCode = "404", description = "Destination not found")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Destinations.class),
                    examples = @ExampleObject(value = "{\"name\": \"Tokyo Tower Updated\", \"description\": \"Updated description\", \"region\": {\"id\": 1}, \"category\": {\"id\": 2}}")))
    public ResponseEntity<Destinations> updateDestination(
            @PathVariable Long id,
            @RequestBody Destinations destinationDetails) {
        Optional<Destinations> destination = destinationsRepository.findById(id);

        if (destination.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Destinations existingDestination = destination.get();
        existingDestination.setName(destinationDetails.getName());
        existingDestination.setDescription(destinationDetails.getDescription());

        if (destinationDetails.getRegion() != null) {
            Optional<Region> region = regionRepository.findById(destinationDetails.getRegion().getId());
            region.ifPresent(existingDestination::setRegion);
        }

        if (destinationDetails.getCategory() != null) {
            Optional<Category> category = categoryRepository.findById(destinationDetails.getCategory().getId());
            category.ifPresent(existingDestination::setCategory);
        }

        Destinations updatedDestination = destinationsRepository.save(existingDestination);
        return ResponseEntity.ok(updatedDestination);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a destination", description = "Delete a destination by its ID")
    @ApiResponse(responseCode = "204", description = "Destination deleted successfully")
    @ApiResponse(responseCode = "404", description = "Destination not found")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        Optional<Destinations> destination = destinationsRepository.findById(id);

        if (destination.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        destinationsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

