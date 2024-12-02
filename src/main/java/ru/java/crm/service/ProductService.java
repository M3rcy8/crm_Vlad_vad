package ru.java.crm.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.java.crm.dto.ProductDto;
import ru.java.crm.entity.Product;
import ru.java.crm.mapper.ProductMapper;
import ru.java.crm.repository.ProductRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    // Получить все продукты
    public List<ProductDto> getAllProducts() {
        log.info("Get all products");
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .toList();
    }

    // Создать новый продукт
    public ProductDto createProduct(ProductDto product) {
        log.info("Create product: {}", product);
        final var entity = productMapper.toEntity(product);
        final var savedProduct = productRepository.save(entity);
        return productMapper.toDto(savedProduct);
    }

    // Получить продукт по ID
    public ProductDto getProductById(Long productId) {
        log.info("Get product by ID: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return productMapper.toDto(product);
    }

    // Обновить продукт
    public ProductDto updateProduct(Long productId, ProductDto updatedProduct) {
        log.info("Update product ID: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());

        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    // Удалить продукт
    public void deleteProduct(Long productId) {
        log.info("Delete product with ID: {}", productId);
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product not found");
        }
        productRepository.deleteById(productId);
    }
}
