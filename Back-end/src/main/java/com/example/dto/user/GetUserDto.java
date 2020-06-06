package com.example.dto.user;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Model that represents User. Used only for GET requests")
public class GetUserDto {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String photo;
    private String aboutUser;
    private String phoneNumber;
    private String dateOfBirth;
    private String trustLevel;
    private boolean blocked;

}
