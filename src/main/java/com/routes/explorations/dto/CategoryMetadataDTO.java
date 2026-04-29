package com.routes.explorations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMetadataDTO {
    private Long id;
    private String name;
    private String slug;
    private String emoji;
    private String imageUrl;
    private Integer destinationCount;
    private Integer sortOrder;
}

