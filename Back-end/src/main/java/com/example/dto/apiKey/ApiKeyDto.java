package com.example.dto.apiKey;

import io.swagger.annotations.ApiModel;
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
@ApiModel(description = "Model that represents API key. Used for giving access only for authenticated users.")
public class ApiKeyDto {

    @NotNull(message = "{user.api.key.null}")
    @NotBlank(message = "{user.api.key.blank}")
    private String apiKey;

}
