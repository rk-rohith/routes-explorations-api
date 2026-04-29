package com.routes.explorations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDTO {
    private Long totalDestinations;
    private Long totalRegions;
    private Long totalCategories;
    private Long totalTags;
    private Long featuredDestinationCount;
}

