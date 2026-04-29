package com.routes.explorations.repository;

import com.routes.explorations.entity.DestinationCategory;
import com.routes.explorations.entity.DestinationCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DestinationCategoryRepository extends JpaRepository<DestinationCategory, DestinationCategoryId> {
    @Query("SELECT dc FROM DestinationCategory dc WHERE dc.id.destinationId = :destinationId")
    List<DestinationCategory> findByDestinationId(Long destinationId);

    @Query("SELECT dc FROM DestinationCategory dc WHERE dc.id.categoryId = :categoryId")
    List<DestinationCategory> findByCategoryId(Long categoryId);

    @Query("SELECT dc FROM DestinationCategory dc WHERE dc.id.destinationId = :destinationId AND dc.isPrimary = true")
    Optional<DestinationCategory> findByDestinationIdAndIsPrimaryTrue(Long destinationId);
}

