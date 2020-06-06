package com.example.dto.task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Model that represents Task. Used for UPDATE and CREATE requests")
public class TaskDto {

    @NotNull(message = "{task.title.null}")
    @NotBlank(message = "{task.title.blank}")
    @Size(min = 5, max = 100, message = "{task.title.size}")
    @ApiModelProperty(example = "Some task title", notes = "Not blank")
    private String title;

    @NotBlank(message = "{task.description.blank}")
    @NotNull(message = "{task.description.null}")
    @Pattern(regexp = "^[a-z A-z]{10,150}$", message = "{description.regex}")
    @ApiModelProperty(example = "Helping old granny with some housework", notes = "Minimum 15 characters, maximum 150, not blank")
    private String description;

    @Min(value = 2, message = "{task.participants.minimum}")
    @Max(value = 100, message = "{task.participants.maximum}")
    @ApiModelProperty(example = "7")
    private int possibleNumberOfParticipants;

    @NotNull(message = "{task.endDate.null}")
    @NotBlank(message = "{task.endDate.blank}")
    @Pattern(regexp = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
    @ApiModelProperty(example = "2020-12-31")
    private String endDate;

    @NotNull(message = "{user.api.key.null}")
    @NotBlank(message = "{user.api.key.blank}")
    private String apiKey;

    private List<String> photos = new ArrayList<>();

}