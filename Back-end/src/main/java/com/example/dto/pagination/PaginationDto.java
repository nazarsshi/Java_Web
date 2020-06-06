package com.example.dto.pagination;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@ApiModel(description = "Model used for pagination. Used in GET requests.")
public class PaginationDto<T> {

    private List<T> entities;
    private long quantity;
    private long entitiesLeft;

}