package com.routes.explorations.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "itinerary_plans", schema = "routesandexplore")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Destinations destination;

    @Column(name = "plan_label", nullable = false, length = 100)
    private String planLabel;

    @Column(name = "duration_days", nullable = false)
    private Integer durationDays;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

    @OneToMany(mappedBy = "itineraryPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItineraryStep> steps;
}

