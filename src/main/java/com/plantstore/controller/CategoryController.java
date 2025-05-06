package com.plantstore.controller;

import com.plantstore.dto.CategoryInfoDTO;
import com.plantstore.dto.CategoryResponseDTO;
import com.plantstore.dto.PageResponse;
import com.plantstore.entity.Category;
import com.plantstore.service.CategoryService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.Operation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category API")
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;




    @Operation(summary = "Create a new category", description = "Tạo mới một danh mục cây")
    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @Operation(summary = "Update an existing category", description = "Cập nhật thông tin một danh mục cây")
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable long id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(updatedCategory);
    }

    @Operation(summary = "Get category by ID", description = "Lấy thông tin chi tiết của một danh mục cây theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryInfoDTO> getCategoryById(@PathVariable long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @Operation(summary = "Get all categories with pagination", description = "Lấy danh sách tất cả các danh mục cây với phân trang")
    @GetMapping
    public ResponseEntity<PageResponse<CategoryResponseDTO>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryResponseDTO> categoryPage = categoryService.getAllCategories(pageable);

        PageResponse<CategoryResponseDTO> response = new PageResponse<>(
                categoryPage.getContent(),
                categoryPage.getNumber(),
                categoryPage.getSize(),
                categoryPage.getTotalElements(),
                categoryPage.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete category by ID", description = "Xóa một danh mục cây theo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Delete successfully!");
    }
}
