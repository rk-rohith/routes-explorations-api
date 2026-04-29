package com.routes.explorations.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "destination_categories", schema = "routesandexplore")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinationCategory {
    @EmbeddedId
    private DestinationCategoryId id;

    @ManyToOne
    @MapsId("destinationId")
    @JoinColumn(name = "destination_id", nullable = false)
    private Destinations destination;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;
}

