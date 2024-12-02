package ru.java.crm.mapper;

import org.mapstruct.Mapper;
import ru.java.crm.dto.ProductDto;
import ru.java.crm.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);

    Product toEntity(ProductDto dto);
}
