package com.routes.explorations.controller;

import com.routes.explorations.dto.DestinationCardDTO;
import com.routes.explorations.dto.DestinationDetailDTO;
import com.routes.explorations.service.RoutesAndExploreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/routes-and-explore")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class RoutesAndExploreController {

    private final RoutesAndExploreService routesAndExploreService;


    /**
     * Get all destinations with basic information with pagination
     * @param pageable Pagination parameters (page, size, sort)
     * @return Page of destination cards
     */
    @GetMapping("/destinations/all")
    public Page<DestinationCardDTO> getAll(
            @PageableDefault(size = 10) Pageable pageable) {
        return routesAndExploreService.getAllDestinations(pageable);
    }

    /**
     * Get all destinations in a specific region with pagination
     * @param regionId The region ID
     * @param pageable Pagination parameters
     * @return Page of destinations with tags for that region
     */
    @GetMapping("/region/{regionId}")
    public Page<DestinationCardDTO> getByRegion(
            @PathVariable Long regionId,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return routesAndExploreService.getDestinationsByRegion(regionId, pageable);
    }

    /**
     * Get all destinations associated with a specific tag with pagination
     * @param tagId The tag ID
     * @param pageable Pagination parameters
     * @return Page of destinations with complete details
     */
    @GetMapping("/tag/{tagId}")
    public Page<DestinationCardDTO> getByTag(
            @PathVariable Long tagId,
            @PageableDefault(size = 10) Pageable pageable) {
        return routesAndExploreService.getDestinationsByTag(tagId, pageable);
    }

    /**
     * Get complete details of a specific destination
     * @param destinationId The destination ID
     * @return Complete destination details including itineraries, gallery, how to reach, etc.
     */
    @GetMapping("/destination/{destinationId}")
    public ResponseEntity<DestinationDetailDTO> getByDestination(@PathVariable Long destinationId) {
        DestinationDetailDTO destinationDetail = routesAndExploreService.getDestinationDetail(destinationId);
        if (destinationDetail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(destinationDetail);
    }

    /**
     * Get destinations by region slug with pagination
     * @param regionSlug The region slug
     * @param pageable Pagination parameters
     * @return Page of destinations in that region
     */
    @GetMapping("/region/slug/{regionSlug}")
    public Page<DestinationCardDTO> getByRegionSlug(
            @PathVariable String regionSlug,
            @PageableDefault(size = 10) Pageable pageable) {
        return routesAndExploreService.getDestinationsByRegionSlug(regionSlug, pageable);
    }

    /**
     * Get destination by destination slug
     * @param destinationSlug The destination slug
     * @return Complete destination details
     */
    @GetMapping("/destination/slug/{destinationSlug}")
    public ResponseEntity<DestinationDetailDTO> getByDestinationSlug(@PathVariable String destinationSlug) {
        DestinationDetailDTO destinationDetail = routesAndExploreService.getDestinationDetailBySlug(destinationSlug);
        if (destinationDetail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(destinationDetail);
    }
}

