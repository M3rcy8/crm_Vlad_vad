package ru.java.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.java.crm.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);
}