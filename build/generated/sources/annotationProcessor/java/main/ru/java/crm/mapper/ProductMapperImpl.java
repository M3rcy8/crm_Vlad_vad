package ru.java.crm.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.java.crm.dto.ProductDto;
import ru.java.crm.entity.Product;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-12T00:25:50+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDto toDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDto.ProductDtoBuilder productDto = ProductDto.builder();

        productDto.name( product.getName() );
        productDto.price( product.getPrice() );

        return productDto.build();
    }

    @Override
    public Product toEntity(ProductDto dto) {
        if ( dto == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.name( dto.getName() );
        product.price( dto.getPrice() );

        return product.build();
    }
}
