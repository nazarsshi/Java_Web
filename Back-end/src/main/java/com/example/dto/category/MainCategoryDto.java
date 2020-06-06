package com.example.dto.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Model that represents Category. Used only for GET requests")
public class MainCategoryDto {

    @ApiModelProperty(example = "1")
    private int id;

    @ApiModelProperty(example = "Disabled", notes = "Minimum 2 characters, maximum 20, not blank")
    private String name;

    @ApiModelProperty(example = "Helping disabled people", notes = "Minimum 10 characters, maximum 150, not blank")
    private String description;

}
