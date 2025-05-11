package com.plantstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CategoryInfoDTO {
    private long id;
    private String categoryName;
    List<PlantInfoDTO> plants;
}
