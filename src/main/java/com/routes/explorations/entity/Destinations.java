package com.routes.explorations.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "destinations", schema = "routesandexplore")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Destinations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length = 220, unique = true)
    private String slug;

    @Column(nullable = true, length = 200)
    private String tagline;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String overview;

    @Column(name = "short_description", nullable = true, columnDefinition = "TEXT")
    private String shortDescription;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(name = "distance_km")
    private Integer distanceKm;

    @Column(name = "travel_time", length = 50)
    private String travelTime;

    @Column(name = "best_time_to_visit", length = 300)
    private String bestTimeToVisit;

    @Column(name = "is_featured", nullable = false)
    private Boolean isFeatured = false;

    @Column(name = "travel_tips", nullable = false, columnDefinition = "jsonb default '[]'")
    private String travelTips = "[]";

    @Column(name = "things_to_do", nullable = false, columnDefinition = "jsonb default '[]'")
    private String thingsToDo = "[]";

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "location_name", length = 300)
    private String locationName;

    @Column(name = "map_available", nullable = false)
    private Boolean mapAvailable = false;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DestinationCategory> destinationCategories;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TagDestination> tagDestinations;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HowToReach> howToReach;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItineraryPlan> itineraryPlans;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GalleryImage> galleryImages;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = OffsetDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}

