package com.routes.explorations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagInfoDTO {
    private Long id;
    private String label;
    private String slug;
    private Integer sortOrder;
}

