package com.plantstore.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PlantResponseDTO {

    private long id ;

    private String title ;

    private String description;

    private double price ;

    private int quantity ;

    private String model3D;

    private String usdz ;

    private String image;

    private String category ;

}
