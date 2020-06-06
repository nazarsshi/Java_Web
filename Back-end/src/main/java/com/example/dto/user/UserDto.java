package com.example.dto.user;

import com.example.validation.LocalDateType;
import com.example.validation.TrustLevelType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Model that represents User. Used for CREATE and UPDATE requests")
public class UserDto {

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

    @NotNull(message = "{user.email.null}")
    @Size(max = 30, message = "{user.email.size}")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "{user.email.regex}")
    @ApiModelProperty(example = "210372@ukr.net", notes = "Latin characters, numbers and specific symbols, size minimum 9 maximum 30, not blank")
    private String email;

    @Pattern(regexp = "^((\\+38)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{10}$", message = "{user.phoneNumber.regex}")
    @ApiModelProperty(example = "0980258933", notes = "Size minimum 7,maximum 10. Only numeric characters, country code is optional")
    private String phoneNumber;

    //TODO ADD VALIDATION
    @NotNull(message = "{user.photo.null}")
    @NotBlank(message = "{user.photo.blank}")
    private String photo;

    @LocalDateType
    @ApiModelProperty(example = "2000-05-30", notes = "minimum 12, maximum 100")
    private String dateOfBirth;

    @NotNull(message = "{user.trust.level.null}")
    @NotBlank(message = "{user.trust.level.blank}")
    @TrustLevelType
    @ApiModelProperty(example = "Novice")
    private String trustLevel;

    @NotNull(message = "{user.blocked.null}")
    @ApiModelProperty(example = "true", notes = "Field to find out whether user is blocked")
    private boolean blocked;

    @NotNull(message = "{user.api.key.null}")
    @NotBlank(message = "{user.api.key.blank}")
    private String apiKey;

}