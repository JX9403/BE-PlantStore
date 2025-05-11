package com.plantstore.service;

import com.plantstore.dto.PlantResponseDTO;
import com.plantstore.entity.Plant;
import com.plantstore.entity.Category;
import com.plantstore.repository.CategoryRepo;
import com.plantstore.repository.PlantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlantServiceImpl implements PlantService {

    @Autowired
    private PlantRepo plantRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PlantResponseDTO createPlant(Plant plant) {

        System.out.println(plant);
        if (plant.getTitle() == null) {
            throw new RuntimeException("Plant's title is required!");
        }
        if (plant.getDescription() == null) {
            throw new RuntimeException("Plant's description is required!");
        }
        if (plant.getQuantity() <= 0) {
            throw new RuntimeException("Plant's quantity must be greater than 0!");
        }
        if (plant.getPrice() <= 0) {
            throw new RuntimeException("Plant's price must be greater than 0!");
        }

        Plant createdPlant = new Plant();
        createdPlant.setTitle(plant.getTitle());
        createdPlant.setDescription(plant.getDescription());
        createdPlant.setPrice(plant.getPrice());
        createdPlant.setQuantity(plant.getQuantity());

        if (plant.getModel3D() != null) {
            createdPlant.setModel3D(plant.getModel3D());
        }
        if (plant.getImage() != null) {
            createdPlant.setImage(plant.getImage());
        }
        if (plant.getUsdz() != null) {
            createdPlant.setUsdz(plant.getUsdz());
        }
        Optional<Category> category = categoryRepo.findById(plant.getCategory().getCategoryId());
        if(category.isEmpty()){
            throw new RuntimeException("Plant's category not found !");
        }
        createdPlant.setCategory(category.get());

         plantRepo.save(createdPlant);

        return convertToDTO(  plantRepo.save(createdPlant));
    }

    @Override
    public PlantResponseDTO updatePlant(long id, Plant plant) {

        Optional<Plant> optionalPlant = plantRepo.findById(id);

        if (optionalPlant.isEmpty()) {
            throw new RuntimeException("Plant not found!");
        }

        if (plant.getTitle() == null) {
            throw new RuntimeException("Plant's title is required!");
        }
        if (plant.getDescription() == null) {
            throw new RuntimeException("Plant's description is required!");
        }
        if (plant.getQuantity() <= 0) {
            throw new RuntimeException("Plant's quantity must be greater than 0!");
        }
        if (plant.getPrice() <= 0) {
            throw new RuntimeException("Plant's price must be greater than 0!");
        }

        Plant updatedPlant = optionalPlant.get();

        updatedPlant.setTitle(plant.getTitle());
        updatedPlant.setDescription(plant.getDescription());
        updatedPlant.setPrice(plant.getPrice());
        updatedPlant.setQuantity(plant.getQuantity());

        if (plant.getModel3D() != null) {
            updatedPlant.setModel3D(plant.getModel3D());
        }
        if (plant.getImage() != null) {
            updatedPlant.setImage(plant.getImage());
        }
        if (plant.getUsdz() != null) {
            updatedPlant.setUsdz(plant.getUsdz());
        }
        Optional<Category> category = categoryRepo.findById(plant.getCategory().getCategoryId());
        if(category.isEmpty()){
            throw new RuntimeException("Plant's category not found !");
        }
        updatedPlant.setCategory(category.get());


        plantRepo.saveAndFlush(updatedPlant);
        return convertToDTO(updatedPlant);
    }

    @Override
    public void deletePlantById(long id) {
        Optional<Plant> optionalPlant = plantRepo.findById(id);
        if (optionalPlant.isEmpty()) {
            throw new RuntimeException("Plant not found!");
        }
        plantRepo.deleteById(id);
    }

    @Override
    public Page<PlantResponseDTO> getAllPlants(Pageable pageable) {
        return plantRepo.findAll(pageable).map(
                this::convertToDTO
        );
    }

    @Override
    public PlantResponseDTO getPlantById(long id) {
        Optional<Plant> optionalPlant = plantRepo.findById(id);
        if (optionalPlant.isEmpty()) {
            throw new RuntimeException("Plant not found!");
        }
        return convertToDTO(optionalPlant.get());
    }

    private PlantResponseDTO convertToDTO(Plant plant) {
        PlantResponseDTO dto = new PlantResponseDTO();
        dto.setId(plant.getId());
        dto.setTitle(plant.getTitle());
        dto.setDescription(plant.getDescription());
        dto.setPrice(plant.getPrice());
        dto.setQuantity(plant.getQuantity());
        dto.setModel3D(plant.getModel3D());
        dto.setImage(plant.getImage());
        dto.setUsdz(plant.getUsdz());

        dto.setCategory(
                plant.getCategory() != null ? plant.getCategory().getCategoryName() : null
        );
        return dto;
    }

}
