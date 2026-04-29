package com.routes.explorations.repository;

import com.routes.explorations.entity.GalleryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GalleryImageRepository extends JpaRepository<GalleryImage, Long> {
    List<GalleryImage> findByDestinationId(Long destinationId);
    List<GalleryImage> findByDestinationIdOrderBySortOrder(Long destinationId);
    List<GalleryImage> findByIsMomentsTrue();
    List<GalleryImage> findByDestinationIdAndIsMomentsTrue(Long destinationId);
}

