package com.routes.explorations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryStepDTO {
    private Long id;
    private Integer stepNumber;
    private String timeOfDay;
    private String description;
}

