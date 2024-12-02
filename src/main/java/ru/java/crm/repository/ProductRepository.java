package ru.java.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.java.crm.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}



