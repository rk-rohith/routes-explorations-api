package com.routes.explorations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GalleryImageDTO {
    private Long id;
    private String imageUrl;
    private String altText;
    private Boolean isMoments;
    private Integer sortOrder;
}

