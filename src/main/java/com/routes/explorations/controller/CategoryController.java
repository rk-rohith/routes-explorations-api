package com.routes.explorations.controller;

import com.routes.explorations.entity.Category;
import com.routes.explorations.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category Management", description = "APIs for managing categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieve a list of all categories")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all categories")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID", description = "Retrieve a specific category by its ID")
    @ApiResponse(responseCode = "200", description = "Category found")
    @ApiResponse(responseCode = "404", description = "Category not found")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new category", description = "Save a new category to the database")
    @ApiResponse(responseCode = "200", description = "Category created successfully",
            content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Category.class),
                    examples = @ExampleObject(value = "{\"id\": 4, \"name\": \"Beach\", \"description\": \"Beach activities and attractions\"}")))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Category.class),
                    examples = @ExampleObject(value = "{\"name\": \"Beach\", \"description\": \"Beach activities and attractions\"}")))
    public ResponseEntity<Category> saveCategory(
            @RequestBody Category category) {
        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(savedCategory);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a category", description = "Update an existing category by its ID")
    @ApiResponse(responseCode = "200", description = "Category updated successfully")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Category.class),
                    examples = @ExampleObject(value = "{\"name\": \"Beach Updated\", \"description\": \"Updated beach activities\"}")))
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long id,
            @RequestBody Category categoryDetails) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Category existingCategory = category.get();
        existingCategory.setName(categoryDetails.getName());
        existingCategory.setDescription(categoryDetails.getDescription());

        Category updatedCategory = categoryRepository.save(existingCategory);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category", description = "Delete a category by its ID")
    @ApiResponse(responseCode = "204", description = "Category deleted successfully")
    @ApiResponse(responseCode = "404", description = "Category not found")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

