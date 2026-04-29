package com.routes.explorations.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.routes.explorations.dto.*;
import com.routes.explorations.entity.*;
import com.routes.explorations.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoutesAndExploreService {

    private final DestinationsRepository destinationsRepository;
    private final RegionRepository regionRepository;
    private final DestinationCategoryRepository destinationCategoryRepository;
    private final TagDestinationRepository tagDestinationRepository;
    private final HowToReachRepository howToReachRepository;
    private final ItineraryPlanRepository itineraryPlanRepository;
    private final ItineraryStepRepository itineraryStepRepository;
    private final GalleryImageRepository galleryImageRepository;
    private final ObjectMapper objectMapper;


    // Get all destinations as cards with pagination
    public Page<DestinationCardDTO> getAllDestinations(Pageable pageable) {
        Page<Destinations> destinationsPage = destinationsRepository.findAll(pageable);
        List<DestinationCardDTO> content = destinationsPage.getContent().stream()
                .map(this::convertToDestinationCard)
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageable, destinationsPage.getTotalElements());
    }

    // Get all destinations by region with tags with pagination
    public Page<DestinationCardDTO> getDestinationsByRegion(Long regionId, Pageable pageable) {
        List<Destinations> destinations = destinationsRepository.findByRegionIdOrderByName(regionId);
        List<DestinationCardDTO> content = destinations.stream()
                .map(this::convertToDestinationCard)
                .skip((long) pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageable, destinations.size());
    }

    // Get all destinations by tag with pagination
    public Page<DestinationCardDTO> getDestinationsByTag(Long tagId, Pageable pageable) {
        List<TagDestination> tagDestinations = tagDestinationRepository.findByTagId(tagId);
        List<DestinationCardDTO> content = tagDestinations.stream()
                .map(td -> convertToDestinationCard(td.getDestination()))
                .skip((long) pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageable, tagDestinations.size());
    }

    // Get complete detail of a destination
    public DestinationDetailDTO getDestinationDetail(Long destinationId) {
        Optional<Destinations> destination = destinationsRepository.findById(destinationId);
        if (destination.isEmpty()) {
            return null;
        }
        return convertToDestinationDetail(destination.get());
    }

    // Get complete detail of a destination by slug
    public DestinationDetailDTO getDestinationDetailBySlug(String slug) {
        Optional<Destinations> destination = destinationsRepository.findBySlug(slug);
        if (destination.isEmpty()) {
            return null;
        }
        return convertToDestinationDetail(destination.get());
    }

    // Get all destinations by region slug with pagination
    public Page<DestinationCardDTO> getDestinationsByRegionSlug(String regionSlug, Pageable pageable) {
        Optional<Region> region = regionRepository.findBySlug(regionSlug);
        if (region.isEmpty()) {
            return Page.empty(pageable);
        }
        return getDestinationsByRegion(region.get().getId(), pageable);
    }

    // Convert Destinations entity to DestinationCardDTO
    private DestinationCardDTO convertToDestinationCard(Destinations destination) {
        DestinationCardDTO card = new DestinationCardDTO();
        card.setId(destination.getId());
        card.setName(destination.getName());
        card.setImageUrl(destination.getImageUrl());
        card.setShortDescription(destination.getShortDescription());
        card.setDistanceKm(destination.getDistanceKm());
        card.setTravelTime(destination.getTravelTime());
        card.setTagline(destination.getTagline());

        // Set region info
        if (destination.getRegion() != null) {
            card.setRegion(convertToRegionInfo(destination.getRegion()));
        }

        // Set primary category
        Optional<DestinationCategory> primaryCategory = destinationCategoryRepository
                .findByDestinationIdAndIsPrimaryTrue(destination.getId());
        if (primaryCategory.isPresent()) {
            card.setPrimaryCategory(convertToCategoryInfo(primaryCategory.get().getCategory()));
        }


        return card;
    }

    // Convert Destinations entity to DestinationDetailDTO
    private DestinationDetailDTO convertToDestinationDetail(Destinations destination) {
        DestinationDetailDTO detail = new DestinationDetailDTO();
        detail.setId(destination.getId());
        detail.setName(destination.getName());
        detail.setSlug(destination.getSlug());
        detail.setTagline(destination.getTagline());
        detail.setOverview(destination.getOverview());
        detail.setShortDescription(destination.getShortDescription());
        detail.setImageUrl(destination.getImageUrl());
        detail.setDistanceKm(destination.getDistanceKm());
        detail.setTravelTime(destination.getTravelTime());
        detail.setBestTimeToVisit(destination.getBestTimeToVisit());
        detail.setIsFeatured(destination.getIsFeatured());
        detail.setTravelTips(destination.getTravelTips());
        detail.setThingsToDo(destination.getThingsToDo());
        detail.setLatitude(destination.getLatitude());
        detail.setLongitude(destination.getLongitude());
        detail.setLocationName(destination.getLocationName());
        detail.setMapAvailable(destination.getMapAvailable());

        // Set region info
        if (destination.getRegion() != null) {
            detail.setRegion(convertToRegionInfo(destination.getRegion()));
        }

        // Set all categories
        List<DestinationCategory> categories = destinationCategoryRepository.findByDestinationId(destination.getId());
        List<CategoryInfoDTO> categoryDTOs = categories.stream()
                .map(dc -> convertToCategoryInfo(dc.getCategory()))
                .collect(Collectors.toList());
        detail.setCategories(categoryDTOs);

        // Set tags
        List<TagDestination> tagDestinations = tagDestinationRepository.findByDestinationId(destination.getId());
        List<TagInfoDTO> tags = tagDestinations.stream()
                .map(td -> convertToTagInfo(td.getTag()))
                .collect(Collectors.toList());
        detail.setTags(tags);

        // Set how to reach
        List<HowToReach> howToReach = howToReachRepository.findByDestinationIdOrderBySortOrder(destination.getId());
        List<HowToReachDTO> howToReachDTOs = howToReach.stream()
                .map(this::convertToHowToReachDTO)
                .collect(Collectors.toList());
        detail.setHowToReachList(howToReachDTOs);

        // Set itinerary plans
        List<ItineraryPlan> itineraryPlans = itineraryPlanRepository.findByDestinationIdOrderBySortOrder(destination.getId());
        List<ItineraryPlanDTO> itineraryPlanDTOs = itineraryPlans.stream()
                .map(this::convertToItineraryPlanDTO)
                .collect(Collectors.toList());
        detail.setItineraryPlans(itineraryPlanDTOs);

        // Set gallery images
        List<GalleryImage> galleryImages = galleryImageRepository.findByDestinationIdOrderBySortOrder(destination.getId());
        List<GalleryImageDTO> galleryImageDTOs = galleryImages.stream()
                .map(this::convertToGalleryImageDTO)
                .collect(Collectors.toList());
        detail.setGalleryImages(galleryImageDTOs);

        return detail;
    }

    // Helper methods to convert entities to DTOs
    private RegionInfoDTO convertToRegionInfo(Region region) {
        if (region == null) return null;
        return new RegionInfoDTO(
                region.getId(),
                region.getName(),
                region.getSlug(),
                region.getImageUrl(),
                region.getStatus()
        );
    }

    private CategoryInfoDTO convertToCategoryInfo(Category category) {
        if (category == null) return null;
        return new CategoryInfoDTO(
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getEmoji(),
                category.getImageUrl(),
                category.getSortOrder()
        );
    }

    private TagInfoDTO convertToTagInfo(Tags tag) {
        if (tag == null) return null;
        return new TagInfoDTO(
                tag.getId(),
                tag.getLabel(),
                tag.getSlug(),
                tag.getSortOrder()
        );
    }

    private HowToReachDTO convertToHowToReachDTO(HowToReach howToReach) {
        if (howToReach == null) return null;
        return new HowToReachDTO(
                howToReach.getId(),
                howToReach.getMode(),
                howToReach.getDescription(),
                howToReach.getSortOrder()
        );
    }

    private ItineraryPlanDTO convertToItineraryPlanDTO(ItineraryPlan plan) {
        if (plan == null) return null;
        List<ItineraryStep> steps = itineraryStepRepository.findByItineraryPlanIdOrderByStepNumber(plan.getId());
        List<ItineraryStepDTO> stepDTOs = steps.stream()
                .map(this::convertToItineraryStepDTO)
                .collect(Collectors.toList());
        return new ItineraryPlanDTO(
                plan.getId(),
                plan.getPlanLabel(),
                plan.getDurationDays(),
                plan.getSortOrder(),
                stepDTOs
        );
    }

    private ItineraryStepDTO convertToItineraryStepDTO(ItineraryStep step) {
        if (step == null) return null;
        return new ItineraryStepDTO(
                step.getId(),
                step.getStepNumber(),
                step.getTimeOfDay(),
                step.getDescription()
        );
    }

    private GalleryImageDTO convertToGalleryImageDTO(GalleryImage image) {
        if (image == null) return null;
        return new GalleryImageDTO(
                image.getId(),
                image.getImageUrl(),
                image.getAltText(),
                image.getIsMoments(),
                image.getSortOrder()
        );
    }

    private List<String> parseJsonbToList(String jsonbData) {
        if (jsonbData == null || jsonbData.isEmpty() || "[]".equals(jsonbData)) {
            return new ArrayList<>();
        }
        try {
            return Arrays.asList(objectMapper.readValue(jsonbData, String[].class));
        } catch (Exception e) {
            log.warn("Failed to parse JSONB data: {}", jsonbData);
            return new ArrayList<>();
        }
    }
}

