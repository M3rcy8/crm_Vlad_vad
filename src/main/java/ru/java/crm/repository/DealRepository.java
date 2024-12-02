package ru.java.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.java.crm.entity.Deal;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {
    List<Deal> findByCustomerId(Long customerId);
}
