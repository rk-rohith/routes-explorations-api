package com.routes.explorations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryPlanDTO {
    private Long id;
    private String planLabel;
    private Integer durationDays;
    private Integer sortOrder;
    private List<ItineraryStepDTO> steps;
}

