package com.example.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Model that represents User. Used for UPDATE requests")
public class UpdateUserDto {

    @NotNull(message = "{user.name.null}")
    @Pattern(regexp = "^[a-zA-z]{2,20}$", message = "{user.name.regex}")
    @ApiModelProperty(example = "Nazar", notes = "Minimum 2 characters, maximum 20, not blank")
    private String firstName;

    @NotNull(message = "{user.surname.null}")
    @Pattern(regexp = "^[a-zA-z]{2,20}$", message = "{user.surname.regex}")
    @ApiModelProperty(example = "Koval", notes = "Minimum 2 characters, maximum 20, not blank")
    private String lastName;

    @NotNull(message = "{user.about.user.null}")
    @Pattern(regexp = "^[a-zA-z]{2,255}$", message = "{user.about.user.regex}")
    @ApiModelProperty(example = "Lorem ipsum kfldkflsdkl", notes = "Minimum 2 characters, maximum 255, not blank")
    private String aboutUser;

    @NotNull(message = "{user.photo.null}")
    @NotBlank(message = "{user.photo.blank}")
    private String photo;

    @NotNull(message = "{user.api.key.null}")
    @NotBlank(message = "{user.api.key.blank}")
    private String apiKey;

}
