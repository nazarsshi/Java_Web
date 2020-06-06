package com.example.dto.task;

import com.example.dto.category.MainCategoryDto;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Model that represents Task. Accessible for GET requests.")
public class CreatedTaskDto {

    private int id;
    private String title;
    private String description;
    private String creationDate;
    private int numberOfParticipants;
    private int approvedParticipants;
    private String endDate;
    private String status;
    private String priority;
    private MainCategoryDto category;

}
