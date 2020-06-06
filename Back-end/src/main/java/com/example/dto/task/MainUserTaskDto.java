package com.example.dto.task;

import com.example.dto.user.GetUserDto;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Transitive table representation. Used for business logic")
public class MainUserTaskDto {

    private GetUserDto user;
    private String comment;
    private String participationDate;
    private boolean approved;

}