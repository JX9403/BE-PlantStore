package com.plantstore.service;

import com.plantstore.dto.PlantResponseDTO;
import com.plantstore.entity.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlantService {
PlantResponseDTO createPlant (Plant plant );
PlantResponseDTO updatePlant ( long id, Plant plant );
void deletePlantById ( long id);
Page<PlantResponseDTO> getAllPlants (Pageable pageable);
PlantResponseDTO getPlantById (long id);
}
