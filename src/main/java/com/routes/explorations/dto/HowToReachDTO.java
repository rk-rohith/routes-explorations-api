package com.routes.explorations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HowToReachDTO {
    private Long id;
    private String mode;
    private String description;
    private Integer sortOrder;
}

