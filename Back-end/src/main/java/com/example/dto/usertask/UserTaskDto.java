package com.example.dto.usertask;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Transitive table representation. Used for business logic")
public class UserTaskDto {

    @ApiModelProperty(example = "Some user`s comment", notes = "Latin and numeric characters. Minimum: 15, maximum: 150."
            + " Field which contains user`s suggestions concerning his/her help")
    private String comment;

    @NotNull(message = "{user.api.key.null}")
    @NotBlank(message = "{user.api.key.blank}")
    private String apiKey;

}
