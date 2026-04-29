package com.routes.explorations.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "how_to_reach", schema = "routesandexplore")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HowToReach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Destinations destination;

    @Column(nullable = false, length = 20)
    private String mode;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;
}

