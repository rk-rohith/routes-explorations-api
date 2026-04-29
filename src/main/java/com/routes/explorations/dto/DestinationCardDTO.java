package com.routes.explorations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinationCardDTO {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String imageUrl;
    private String shortDescription;
    private Integer distanceKm;
    private String travelTime;
    private String tagline;
    private RegionInfoDTO region;
    private CategoryInfoDTO primaryCategory;
}

