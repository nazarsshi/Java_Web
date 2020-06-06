package com.example.dto.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Model used for user`s login.")
public class LoginDto {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "{user.email.regex}")
    @ApiModelProperty(example = "user@gmail.com", notes = "Only latin letter, size minimum 9 maximum 30")
    private String email;

    @NotNull(message = "{user.password.null}")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\w!#$%&*+?@^]{8,30}$", message = "{user.password.regex}")
    @ApiModelProperty(example = "testTest0", notes = "Only latin letter, size minimum 8 maximum 30, "
            + "at least one uppercase letter, one lowercase letter and one number")
    private String password;

}
