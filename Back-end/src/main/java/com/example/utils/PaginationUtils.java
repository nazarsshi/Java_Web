package com.example.utils;

import com.example.dto.pagination.PaginationDto;
import com.example.error.PaginationException;

import java.util.List;

public class PaginationUtils {

    public static <T> PaginationDto<T> paginate(List<T> entities, int offset, int limit) {
        if (offset < 0) {
            throw new PaginationException("Page or size can't be less or equals to 0");
        }

        int end = offset + limit;

        int quantity = entities.size();
        int entityLeft = Math.max(quantity - end, 0);

        if (offset < quantity && end > quantity) {
            end = quantity;
        }

        if (offset > quantity || end > quantity) {
            throw new PaginationException("There is no such quantity of entities");
        }
        entities = entities.subList(offset, end);

        return PaginationDto.<T>builder()
                .entities(entities)
                .quantity(quantity)
                .entitiesLeft(entityLeft)
                .build();
    }

}
