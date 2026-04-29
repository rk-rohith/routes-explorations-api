package com.routes.explorations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FooterSectionDTO {
    private String companyDescription;
    private String story;
    private List<SocialMediaDTO> socialMedia;
    private Map<String, List<FooterLinkDTO>> sections;
    private String email;
    private String phone;
    private List<FooterLinkDTO> bottomLinks;
    private String copyright;
}

