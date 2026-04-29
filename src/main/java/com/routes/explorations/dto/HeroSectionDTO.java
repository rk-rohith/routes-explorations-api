package com.routes.explorations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeroSectionDTO {
    private String title;
    private String subtitle;
    private String backgroundImageUrl;
    private String ctaButtonText;
    private String ctaButtonLink;
}

