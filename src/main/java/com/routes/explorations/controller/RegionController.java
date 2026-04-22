package com.routes.explorations.controller;

import com.routes.explorations.entity.Region;
import com.routes.explorations.repository.RegionRepository;
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
@RequestMapping("/api/regions")
@Tag(name = "Region Management", description = "APIs for managing regions")
public class RegionController {

    @Autowired
    private RegionRepository regionRepository;

    @GetMapping
    @Operation(summary = "Get all regions", description = "Retrieve a list of all regions")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all regions")
    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get region by ID", description = "Retrieve a specific region by its ID")
    @ApiResponse(responseCode = "200", description = "Region found")
    @ApiResponse(responseCode = "404", description = "Region not found")
    public ResponseEntity<Region> getRegionById(@PathVariable Long id) {
        Optional<Region> region = regionRepository.findById(id);
        return region.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new region", description = "Save a new region to the database")
    @ApiResponse(responseCode = "200", description = "Region created successfully",
            content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Region.class),
                    examples = @ExampleObject(value = "{\"id\": 4, \"name\": \"West\", \"description\": \"Western region\"}")))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Region.class),
                    examples = @ExampleObject(value = "{\"name\": \"West\", \"description\": \"Western region\"}")))
    public ResponseEntity<Region> saveRegion(
            @RequestBody Region region) {
        Region savedRegion = regionRepository.save(region);
        return ResponseEntity.ok(savedRegion);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a region", description = "Update an existing region by its ID")
    @ApiResponse(responseCode = "200", description = "Region updated successfully")
    @ApiResponse(responseCode = "404", description = "Region not found")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Region.class),
                    examples = @ExampleObject(value = "{\"name\": \"West Updated\", \"description\": \"Updated western region\"}")))
    public ResponseEntity<Region> updateRegion(
            @PathVariable Long id,
            @RequestBody Region regionDetails) {
        Optional<Region> region = regionRepository.findById(id);

        if (region.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Region existingRegion = region.get();
        existingRegion.setName(regionDetails.getName());
        existingRegion.setDescription(regionDetails.getDescription());

        Region updatedRegion = regionRepository.save(existingRegion);
        return ResponseEntity.ok(updatedRegion);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a region", description = "Delete a region by its ID")
    @ApiResponse(responseCode = "204", description = "Region deleted successfully")
    @ApiResponse(responseCode = "404", description = "Region not found")
    public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
        Optional<Region> region = regionRepository.findById(id);

        if (region.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        regionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

