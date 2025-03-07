package org.example.server_mobile.mapper;

import org.example.server_mobile.dto.request.CategoryRequest;
import org.example.server_mobile.dto.response.CategoryResponse;
import org.example.server_mobile.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest category);

    CategoryResponse toCategoryResponse(Category category);
}
