package com.routes.explorations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionMetadataDTO {
    private Long id;
    private String name;
    private String slug;
    private String imageUrl;
    private String status;
    private Integer destinationCount;
}

