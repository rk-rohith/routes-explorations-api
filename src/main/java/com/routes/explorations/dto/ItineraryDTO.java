package com.routes.explorations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryDTO {
    private List<String> oneDay;
    private List<String> twoDay;
    private List<String> threeDay;
}

