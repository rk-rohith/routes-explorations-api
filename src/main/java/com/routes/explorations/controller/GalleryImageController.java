package com.routes.explorations.controller;

import com.routes.explorations.entity.GalleryImage;
import com.routes.explorations.repository.GalleryImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gallery-images")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class GalleryImageController {

    private final GalleryImageRepository galleryImageRepository;

    @GetMapping
    public List<GalleryImage> getAllGalleryImages() {
        return galleryImageRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GalleryImage> getGalleryImageById(@PathVariable Long id) {
        Optional<GalleryImage> image = galleryImageRepository.findById(id);
        return image.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/destination/{destinationId}")
    public List<GalleryImage> getGalleryImagesByDestination(@PathVariable Long destinationId) {
        return galleryImageRepository.findByDestinationIdOrderBySortOrder(destinationId);
    }

    @GetMapping("/destination/{destinationId}/moments")
    public List<GalleryImage> getMomentsByDestination(@PathVariable Long destinationId) {
        return galleryImageRepository.findByDestinationIdAndIsMomentsTrue(destinationId);
    }

    @GetMapping("/moments")
    public List<GalleryImage> getAllMoments() {
        return galleryImageRepository.findByIsMomentsTrue();
    }

    @PostMapping
    public GalleryImage createGalleryImage(@RequestBody GalleryImage image) {
        return galleryImageRepository.save(image);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GalleryImage> updateGalleryImage(@PathVariable Long id, @RequestBody GalleryImage imageDetails) {
        Optional<GalleryImage> image = galleryImageRepository.findById(id);
        if (image.isPresent()) {
            GalleryImage img = image.get();
            img.setDestination(imageDetails.getDestination());
            img.setImageUrl(imageDetails.getImageUrl());
            img.setAltText(imageDetails.getAltText());
            img.setIsMoments(imageDetails.getIsMoments());
            img.setSortOrder(imageDetails.getSortOrder());
            return ResponseEntity.ok(galleryImageRepository.save(img));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGalleryImage(@PathVariable Long id) {
        if (galleryImageRepository.existsById(id)) {
            galleryImageRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

