package com.plantstore.service;

import com.plantstore.dto.CategoryInfoDTO;
import com.plantstore.dto.CategoryResponseDTO;
import com.plantstore.dto.PlantInfoDTO;
import com.plantstore.dto.PlantResponseDTO;
import com.plantstore.entity.Category;
import com.plantstore.repository.CategoryRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepo categoryRepo ;


    @Override
    @Transactional

    public Category createCategory(Category category) {
        Category newCategory = new Category();
        if(category.getCategoryName() != null ){
            newCategory.setCategoryName(category.getCategoryName());
        }

        categoryRepo.save(newCategory);
        return newCategory;
    }

    @Transactional
    @Override
    public Category updateCategory(long id, Category reqCategory) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);

        if( !optionalCategory.isPresent()){
            throw new RuntimeException(("Category not found!"));
        }

        Category updateCategory = optionalCategory.get();
        if( reqCategory.getCategoryName() == null ){
            throw new RuntimeException(("Category's name is required!"));
        }

        updateCategory.setCategoryName(reqCategory.getCategoryName());

        return categoryRepo.saveAndFlush(updateCategory);
    }

    @Override
    public Page<CategoryResponseDTO> getAllCategories(Pageable pageable) {
        return categoryRepo.findAll(pageable).map(
                this::convertToDTO
        );
    }

    @Override
    public CategoryInfoDTO getCategoryById(long id) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);

        if( !optionalCategory.isPresent()){
            throw new RuntimeException(("Category not found!"));
        }
        Category category = optionalCategory.get();

        return convertToInfoDTO(category);
    }

    @Override
    public void deleteCategoryById(long id) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);

        if( !optionalCategory.isPresent()){
            throw new RuntimeException(("Category not found!"));
        }

        categoryRepo.deleteById(id);
    }

    public CategoryResponseDTO convertToDTO(Category category) {
        if (category == null) {
            return null;
        }
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        return dto;
    }

    private CategoryInfoDTO convertToInfoDTO(Category category) {
        if (category == null) {
            return null;
        }

        CategoryInfoDTO dto = new CategoryInfoDTO();
        dto.setId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());

        if (category.getListPlants() != null) {
            List<PlantInfoDTO> plantDTOs = category.getListPlants().stream()
                    .map(plant -> {
                        PlantInfoDTO plantDTO = new PlantInfoDTO();
                        plantDTO.setId(plant.getId());
                        plantDTO.setTitle(plant.getTitle());

                        return plantDTO;
                    })
                    .collect(Collectors.toList());

            dto.setPlants(plantDTOs);
        }

        return dto;
    }


}
