package ru.java.crm.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.java.crm.dto.CategoryDto;
import ru.java.crm.entity.Category;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-12T00:25:50+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDto toDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto.CategoryDtoBuilder categoryDto = CategoryDto.builder();

        categoryDto.name( category.getName() );

        return categoryDto.build();
    }

    @Override
    public Category toEntity(CategoryDto dto) {
        if ( dto == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.name( dto.getName() );

        return category.build();
    }
}
