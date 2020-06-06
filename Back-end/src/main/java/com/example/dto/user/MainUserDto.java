package com.example.dto.user;

import com.example.dto.task.GetTaskDto;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Model that represents User. Used only for GET requests")
public class MainUserDto {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String aboutUser;
    private String photo;
    private String phoneNumber;
    private String dateOfBirth;
    private String trustLevel;
    private Set<GetTaskDto> createdTasks;
    private boolean blocked;

}
