package com.routes.explorations.controller;

import com.routes.explorations.entity.Category;
import com.routes.explorations.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<Category> getAllCategories() {
        log.info("Fetching all categories");
        List<Category> categories = categoryRepository.findAll();
        log.info("Retrieved {} categories", categories.size());
        return categories;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        log.info("Fetching category with ID: {}", id);
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            log.info("Category found with ID: {}", id);
        } else {
            log.warn("Category not found with ID: {}", id);
        }
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> saveCategory(
            @RequestBody Category category) {
        log.info("Creating new category: {}", category.getName());
        Category savedCategory = categoryRepository.save(category);
        log.info("Category created successfully with ID: {}", savedCategory.getId());
        return ResponseEntity.ok(savedCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long id,
            @RequestBody Category categoryDetails) {
        log.info("Updating category with ID: {}", id);
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            log.warn("Category not found for update with ID: {}", id);
            return ResponseEntity.notFound().build();
        }

        Category existingCategory = category.get();
        existingCategory.setName(categoryDetails.getName());

        Category updatedCategory = categoryRepository.save(existingCategory);
        log.info("Category updated successfully with ID: {}", id);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        log.info("Deleting category with ID: {}", id);
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            log.warn("Category not found for deletion with ID: {}", id);
            return ResponseEntity.notFound().build();
        }

        categoryRepository.deleteById(id);
        log.info("Category deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
