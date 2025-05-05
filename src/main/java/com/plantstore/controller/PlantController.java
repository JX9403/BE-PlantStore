package com.plantstore.controller;

import com.plantstore.dto.PageResponse;
import com.plantstore.dto.PlantResponseDTO;
import com.plantstore.entity.Plant;
import com.plantstore.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plant")
public class PlantController {
    @Autowired
   private PlantService plantService;

    @PostMapping
    public ResponseEntity<PlantResponseDTO> create (@RequestBody Plant plant) {
        PlantResponseDTO createdPlant = plantService.createPlant(plant);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlant);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<PlantResponseDTO> update (@PathVariable long id, @RequestBody Plant plant){
        PlantResponseDTO updatedPlant = plantService.updatePlant(id, plant);

        return ResponseEntity.ok(updatedPlant);
    }

    @GetMapping ("/{id}")
    public  ResponseEntity<PlantResponseDTO> getPlantById ( @PathVariable long id ){
        return ResponseEntity.ok(plantService.getPlantById(id));
    }

    @GetMapping
    public  ResponseEntity<PageResponse<PlantResponseDTO>> getAllPlants (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<PlantResponseDTO> plantPage = plantService.getAllPlants(pageable);

        PageResponse<PlantResponseDTO> response = new PageResponse<>(
                plantPage.getContent(),
                plantPage.getNumber(),
                plantPage.getSize(),
                plantPage.getTotalElements(),
                plantPage.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete (
            @PathVariable long id
    ){
        plantService.deletePlantById(id);
        return ResponseEntity.ok("Delete successfully!") ;
    }

}
