package ru.java.crm.mapper;

import org.mapstruct.Mapper;
import ru.java.crm.dto.CategoryDto;
import ru.java.crm.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto dto);
}
