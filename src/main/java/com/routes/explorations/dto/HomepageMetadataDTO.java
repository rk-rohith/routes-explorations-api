package com.routes.explorations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomepageMetadataDTO {
    private HeroSectionDTO heroSection;
    private List<CategoryMetadataDTO> categories;
    private List<RegionMetadataDTO> regions;
    private List<DestinationCardDTO> featuredDestinations;
    private List<FeatureHighlightDTO> features;
    private StatisticsDTO statistics;
    private FooterSectionDTO footer;
}

