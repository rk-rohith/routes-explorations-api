package com.routes.explorations.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "itinerary_steps", schema = "routesandexplore")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private ItineraryPlan itineraryPlan;

    @Column(name = "step_number", nullable = false)
    private Integer stepNumber;

    @Column(name = "time_of_day", length = 50)
    private String timeOfDay;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
}

