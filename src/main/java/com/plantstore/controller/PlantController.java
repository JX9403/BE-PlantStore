package com.plantstore.controller;

import com.plantstore.dto.PageResponse;
import com.plantstore.dto.PlantResponseDTO;
import com.plantstore.entity.Plant;
import com.plantstore.service.PlantService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.Operation;
//import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Plant API")
@RestController
@RequestMapping("/api/plant")
public class PlantController {

    @Autowired
    private PlantService plantService;

    @Operation(summary = "Create a new plant", description = "Tạo một loài cây mới")
    @PostMapping
    public ResponseEntity<PlantResponseDTO> create(@RequestBody Plant plant) {
        PlantResponseDTO createdPlant = plantService.createPlant(plant);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlant);
    }

    @Operation(summary = "Update an existing plant", description = "Cập nhật thông tin loài cây theo ID")
    @PutMapping("/{id}")
    public ResponseEntity<PlantResponseDTO> update(
             @PathVariable long id,
            @RequestBody Plant plant) {
        PlantResponseDTO updatedPlant = plantService.updatePlant(id, plant);
        return ResponseEntity.ok(updatedPlant);
    }

    @Operation(summary = "Get plant details by ID", description = "Lấy thông tin chi tiết loài cây theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<PlantResponseDTO> getPlantById( @PathVariable long id) {
        return ResponseEntity.ok(plantService.getPlantById(id));
    }

    @Operation(summary = "Get all plants", description = "Lấy danh sách tất cả các loài cây với phân trang")
    @GetMapping
    public ResponseEntity<PageResponse<PlantResponseDTO>> getAllPlants(
             @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size) {

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
    @Operation(summary = "Delete a plant", description = "Xóa một loài cây theo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete( @PathVariable long id) {
        plantService.deletePlantById(id);
        return ResponseEntity.ok("Delete successfully!");
    }
}
