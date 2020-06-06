package com.example.dto.user;

import com.example.dto.task.GetTaskDto;
import com.example.dto.task.MainTaskDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Transitive table representation. Used for business logic")
public class MainTaskUserDto {

    private GetTaskDto task;
    private String participationDate;
    private String comment;
    private boolean approved;

}
