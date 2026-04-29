package com.routes.explorations.controller;

import com.routes.explorations.entity.Tags;
import com.routes.explorations.repository.TagsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tags")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class TagsController {

    private final TagsRepository tagsRepository;


    @GetMapping
    public List<Tags> getAllTags() {
        return tagsRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tags> getTagById(@PathVariable Long id) {
        Optional<Tags> tag = tagsRepository.findById(id);
        return tag.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<Tags> getTagBySlug(@PathVariable String slug) {
        Optional<Tags> tag = tagsRepository.findBySlug(slug);
        return tag.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/region/{regionId}")
    public List<Tags> getTagsByRegion(@PathVariable Long regionId) {
        return tagsRepository.findByRegionIdOrderBySortOrder(regionId);
    }

    @PostMapping
    public Tags createTag(@RequestBody Tags tag) {
        return tagsRepository.save(tag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tags> updateTag(@PathVariable Long id, @RequestBody Tags tagDetails) {
        Optional<Tags> tag = tagsRepository.findById(id);
        if (tag.isPresent()) {
            Tags t = tag.get();
            t.setLabel(tagDetails.getLabel());
            t.setSlug(tagDetails.getSlug());
            t.setRegion(tagDetails.getRegion());
            t.setSortOrder(tagDetails.getSortOrder());
            return ResponseEntity.ok(tagsRepository.save(t));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        if (tagsRepository.existsById(id)) {
            tagsRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

