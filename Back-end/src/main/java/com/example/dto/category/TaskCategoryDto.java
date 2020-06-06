package com.example.dto.category;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Model that represents category stored in a particular task. Used in GET requests.")
public class TaskCategoryDto {

    private int id;
    private String name;
    private String description;

}
