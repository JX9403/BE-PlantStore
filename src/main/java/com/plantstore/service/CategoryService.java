package com.plantstore.service;

import com.plantstore.dto.CategoryInfoDTO;
import com.plantstore.dto.CategoryResponseDTO;
import com.plantstore.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CategoryService {
     Category createCategory (Category category );
     Category updateCategory ( long id, Category category);
     Page<CategoryResponseDTO> getAllCategories (Pageable pageable);
     CategoryInfoDTO getCategoryById (long id );
     void deleteCategoryById ( long id);
}
