package com.routes.explorations.controller;

import com.routes.explorations.dto.HomepageMetadataDTO;
import com.routes.explorations.service.HomepageMetadataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/homepage")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class HomepageController {

    private final HomepageMetadataService homepageMetadataService;


    /**
     * Get all homepage metadata
     * @return Complete homepage metadata including hero, categories, regions, featured destinations, etc.
     */
    @GetMapping("/metadata")
    public ResponseEntity<HomepageMetadataDTO> getHomepageMetadata() {
        log.info("Fetching homepage metadata");
        HomepageMetadataDTO metadata = homepageMetadataService.getHomepageMetadata();
        log.info("Homepage metadata fetched successfully");
        return ResponseEntity.ok(metadata);
    }
}

