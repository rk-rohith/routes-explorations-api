package com.routes.explorations.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tag_destinations", schema = "routesandexplore")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDestination {
    @EmbeddedId
    private TagDestinationId id;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id", nullable = false)
    private Tags tag;

    @ManyToOne
    @MapsId("destinationId")
    @JoinColumn(name = "destination_id", nullable = false)
    private Destinations destination;
}

