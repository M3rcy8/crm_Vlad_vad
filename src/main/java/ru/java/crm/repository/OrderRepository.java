package ru.java.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.java.crm.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}