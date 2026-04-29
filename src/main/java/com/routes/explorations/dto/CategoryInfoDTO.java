package com.routes.explorations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInfoDTO {
    private Long id;
    private String name;
    private String slug;
    private String emoji;
    private String imageUrl;
    private Integer sortOrder;
}

