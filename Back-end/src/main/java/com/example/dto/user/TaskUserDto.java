package com.example.dto.user;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Model that represents user stored in task. Used in GET requests.")
public class TaskUserDto {

    private int id;
    private String firstName;
    private String lastName;

}
