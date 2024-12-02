package ru.java.crm.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.java.crm.configuration.RabbitMQProperties;
import ru.java.crm.dto.OrderDto;
import ru.java.crm.entity.Customer;
import ru.java.crm.entity.Order;
import ru.java.crm.mapper.OrderMapper;
import ru.java.crm.repository.CustomerRepository;
import ru.java.crm.repository.OrderRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RabbitTemplate rabbitTemplate;
    private final OrderMapper orderMapper;
    private RabbitMQProperties properties;

    // Получить все заказы
    public List<OrderDto> getAllOrders() {
        log.info("Get all orders");
        return orderRepository.findAll().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    // Создать новый заказ
    public OrderDto createOrder(Order order, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());
        Order savedOrder = orderRepository.save(order);
        final var orderDto = orderMapper.toDto(savedOrder);
        rabbitTemplate.convertAndSend(
                properties.getProduce().getOrder().getExchange(),
                properties.getProduce().getOrder().getRoutingKey(),
                orderDto
        );
        return orderDto;
    }

    // Получить заказ по ID
    public OrderDto getOrderById(Long orderId) {
        log.info("Get order by ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return orderMapper.toDto(order);
    }

    // Обновить статус заказа
    public OrderDto updateOrderStatus(Long orderId, String newStatus) {
        log.info("Update order status for ID: {} to {}", orderId, newStatus);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        order.setStatus(newStatus);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    // Удалить заказ
    public void deleteOrder(Long orderId) {
        log.info("Delete order with ID: {}", orderId);
        if (!orderRepository.existsById(orderId)) {
            throw new EntityNotFoundException("Order not found");
        }
        orderRepository.deleteById(orderId);
    }
}
