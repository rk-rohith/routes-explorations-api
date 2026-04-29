package com.routes.explorations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinationDetailDTO {
    private Long id;
    private String name;
    private String slug;
    private String tagline;
    private String overview;
    private String shortDescription;
    private String imageUrl;
    
    private Integer distanceKm;
    private String travelTime;
    private String bestTimeToVisit;
    private Boolean isFeatured;
    private String travelTips;
    private String thingsToDo;
    
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String locationName;
    private Boolean mapAvailable;
    
    private RegionInfoDTO region;
    private List<CategoryInfoDTO> categories;
    private List<TagInfoDTO> tags;
    private List<HowToReachDTO> howToReachList;
    private List<ItineraryPlanDTO> itineraryPlans;
    private List<GalleryImageDTO> galleryImages;
}

