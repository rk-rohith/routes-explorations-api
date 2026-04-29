package com.routes.explorations.service;

import com.routes.explorations.dto.*;
import com.routes.explorations.entity.*;
import com.routes.explorations.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomepageMetadataService {

    private final DestinationsRepository destinationsRepository;
    private final RegionRepository regionRepository;
    private final CategoryRepository categoryRepository;
    private final TagsRepository tagsRepository;

    public HomepageMetadataDTO getHomepageMetadata() {
        log.info("Fetching homepage metadata");
        HomepageMetadataDTO metadata = new HomepageMetadataDTO();

        metadata.setHeroSection(getHeroSection());
        metadata.setCategories(getCategoriesMetadata());
        metadata.setRegions(getRegionsMetadata());
        metadata.setFeaturedDestinations(getFeaturedDestinations());
        metadata.setFeatures(getFeatureHighlights());
        metadata.setStatistics(getStatistics());
        metadata.setFooter(getFooterMetadata());

        log.info("Homepage metadata fetched successfully");
        return metadata;
    }

    private HeroSectionDTO getHeroSection() {
        HeroSectionDTO hero = new HeroSectionDTO();
        hero.setTitle("Routes & Explorations");
        hero.setSubtitle("Discover places, plan journeys, and explore the unknown.");
        hero.setBackgroundImageUrl("https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=1200&h=600&fit=crop");
        hero.setCtaButtonText("Explore Destinations");
        hero.setCtaButtonLink("/explore");
        return hero;
    }

    private List<CategoryMetadataDTO> getCategoriesMetadata() {
        return categoryRepository.findAll().stream().map(category -> {
            CategoryMetadataDTO dto = new CategoryMetadataDTO();
            dto.setId(category.getId());
            dto.setName(category.getName());
            dto.setSlug(category.getSlug());
            dto.setEmoji(category.getEmoji());
            dto.setImageUrl(category.getImageUrl());
            dto.setSortOrder(category.getSortOrder());
            // Count destinations for this category
            long count = destinationsRepository.findAll().stream()
                    .filter(d -> d.getDestinationCategories() != null &&
                            d.getDestinationCategories().stream()
                                    .anyMatch(dc -> dc.getCategory().getId().equals(category.getId())))
                    .count();
            dto.setDestinationCount((int) count);
            return dto;
        }).collect(Collectors.toList());
    }

    private List<RegionMetadataDTO> getRegionsMetadata() {
        return regionRepository.findAll().stream().map(region -> {
            RegionMetadataDTO dto = new RegionMetadataDTO();
            dto.setId(region.getId());
            dto.setName(region.getName());
            dto.setSlug(region.getSlug());
            dto.setImageUrl(region.getImageUrl());
            dto.setStatus(region.getStatus());
            // Count destinations for this region
            long count = destinationsRepository.findByRegionId(region.getId()).size();
            dto.setDestinationCount((int) count);
            return dto;
        }).collect(Collectors.toList());
    }

    private List<DestinationCardDTO> getFeaturedDestinations() {
        return destinationsRepository.findByIsFeaturedTrue().stream()
                .map(this::convertToDestinationCard)
                .limit(7)
                .collect(Collectors.toList());
    }

    private List<FeatureHighlightDTO> getFeatureHighlights() {
        List<FeatureHighlightDTO> features = new ArrayList<>();

        features.add(new FeatureHighlightDTO(
                1L,
                "Curated Travel Guides",
                "Handpicked destinations with detailed guides created by travel experts and local enthusiasts."
        ));

        features.add(new FeatureHighlightDTO(
                2L,
                "Real Routes & Itineraries",
                "Practical routes with exact distances, travel times, and step-by-step itineraries for every destination."
        ));

        features.add(new FeatureHighlightDTO(
                3L,
                "Best Time + Hidden Insights",
                "Know exactly when to visit and discover hidden gems that only locals know about."
        ));

        features.add(new FeatureHighlightDTO(
                4L,
                "No Fluff, Practical Content",
                "Straight-to-the-point information with actionable tips, budget estimates, and real experiences."
        ));

        return features;
    }

    private StatisticsDTO getStatistics() {
        StatisticsDTO stats = new StatisticsDTO();
        stats.setTotalDestinations((long) destinationsRepository.findAll().size());
        stats.setTotalRegions((long) regionRepository.findAll().size());
        stats.setTotalCategories((long) categoryRepository.findAll().size());
        stats.setTotalTags((long) tagsRepository.findAll().size());
        stats.setFeaturedDestinationCount((long) destinationsRepository.findByIsFeaturedTrue().size());
        return stats;
    }

    private FooterSectionDTO getFooterMetadata() {
        FooterSectionDTO footer = new FooterSectionDTO();

        footer.setCompanyDescription("Discover places, plan journeys, and explore the unknown. Your ultimate guide to destinations across India with curated travel guides and real insights.");

        footer.setStory("Some journeys begin with a plan.\n" +
                "Ours began with a feeling.\n\n" +
                "A quiet urge to leave the familiar behind…\n" +
                "to follow winding roads, wake up in misty hills, stand before ancient temples, and find stories hidden in places most maps don't highlight.\n\n" +
                "Routes & Explorations was born from countless such moments —\n" +
                "early morning rides through fog-covered highways, unplanned stops at unknown villages, conversations with locals, and the realization that the most meaningful travel experiences are often the least expected.\n\n" +
                "But we also saw a gap.\n\n" +
                "Travel was becoming rushed.\n" +
                "Guides were either too generic or too overwhelming.\n" +
                "And somewhere along the way, the essence of exploration was getting lost.\n\n" +
                "So we decided to build something different.\n\n" +
                "Not just a travel website —\n" +
                "but a space that feels like a trusted companion.\n\n" +
                "A place where every destination is thoughtfully curated,\n" +
                "every route is practical and real,\n" +
                "and every guide helps you experience—not just visit—a place.\n\n" +
                "Because travel isn't just about reaching somewhere.\n" +
                "It's about what you feel along the way.\n\n" +
                "The calm of a sunrise.\n" +
                "The thrill of a new road.\n" +
                "The stories you carry back home.\n\n" +
                "And maybe, a small part of you that changes with every journey.\n\n" +
                "This is what Routes & Explorations stands for.\n\n" +
                "Not just places.\n" +
                "But journeys that stay with you.");

        // Social Media
        List<SocialMediaDTO> socialMedia = Arrays.asList(
                new SocialMediaDTO("Instagram", "https://instagram.com/routesandexplorations"),
                new SocialMediaDTO("Facebook", "https://facebook.com/routesandexplorations"),
                new SocialMediaDTO("Twitter", "https://twitter.com/routesandexplorations")
        );
        footer.setSocialMedia(socialMedia);

        // Navigation sections
        java.util.Map<String, List<FooterLinkDTO>> sections = new java.util.HashMap<>();

        sections.put("Explore", Arrays.asList(
                new FooterLinkDTO("All Destinations", "/destinations"),
                new FooterLinkDTO("Categories", "/categories"),
                new FooterLinkDTO("Regions", "/regions"),
                new FooterLinkDTO("Featured Places", "/featured")
        ));

        sections.put("About", Arrays.asList(
                new FooterLinkDTO("Our Story", "/about"),
                new FooterLinkDTO("Contact Us", "/contact")
        ));

        footer.setSections(sections);

        // Contact info
        footer.setEmail("hello@routesandexplorations.com");
        footer.setPhone("+91 12345 67890");

        // Bottom links
        List<FooterLinkDTO> bottomLinks = Arrays.asList(
                new FooterLinkDTO("Privacy Policy", "/privacy"),
                new FooterLinkDTO("Terms of Service", "/terms")
        );
        footer.setBottomLinks(bottomLinks);

        // Copyright
        footer.setCopyright("© 2026 Routes & Explorations. All rights reserved.");

        return footer;
    }

    private DestinationCardDTO convertToDestinationCard(Destinations destination) {
        DestinationCardDTO card = new DestinationCardDTO();
        card.setId(destination.getId());
        card.setName(destination.getName());
        card.setSlug(destination.getSlug());
        card.setImageUrl(destination.getImageUrl());
        card.setShortDescription(destination.getShortDescription());
        card.setDistanceKm(destination.getDistanceKm());
        card.setTravelTime(destination.getTravelTime());
        card.setTagline(destination.getTagline());

        // Set region info
        if (destination.getRegion() != null) {
            RegionInfoDTO regionInfo = new RegionInfoDTO();
            regionInfo.setId(destination.getRegion().getId());
            regionInfo.setName(destination.getRegion().getName());
            regionInfo.setSlug(destination.getRegion().getSlug());
            regionInfo.setImageUrl(destination.getRegion().getImageUrl());
            regionInfo.setStatus(destination.getRegion().getStatus());
            card.setRegion(regionInfo);
        }

        // Set primary category
        if (destination.getDestinationCategories() != null && !destination.getDestinationCategories().isEmpty()) {
            destination.getDestinationCategories().stream()
                    .filter(dc -> dc.getIsPrimary() != null && dc.getIsPrimary())
                    .findFirst()
                    .ifPresent(dc -> {
                        if (dc.getCategory() != null) {
                            CategoryInfoDTO categoryInfo = new CategoryInfoDTO();
                            categoryInfo.setId(dc.getCategory().getId());
                            categoryInfo.setName(dc.getCategory().getName());
                            categoryInfo.setSlug(dc.getCategory().getSlug());
                            categoryInfo.setEmoji(dc.getCategory().getEmoji());
                            categoryInfo.setImageUrl(dc.getCategory().getImageUrl());
                            card.setPrimaryCategory(categoryInfo);
                        }
                    });
        }

        return card;
    }
}

