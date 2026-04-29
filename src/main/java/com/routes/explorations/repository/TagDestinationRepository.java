package com.routes.explorations.repository;

import com.routes.explorations.entity.TagDestination;
import com.routes.explorations.entity.TagDestinationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TagDestinationRepository extends JpaRepository<TagDestination, TagDestinationId> {
    @Query("SELECT td FROM TagDestination td WHERE td.id.tagId = :tagId")
    List<TagDestination> findByTagId(Long tagId);

    @Query("SELECT td FROM TagDestination td WHERE td.id.destinationId = :destinationId")
    List<TagDestination> findByDestinationId(Long destinationId);
}

