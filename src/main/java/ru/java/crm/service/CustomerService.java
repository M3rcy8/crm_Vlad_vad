package ru.java.crm.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.java.crm.dto.CustomerDto;
import ru.java.crm.entity.Customer;
import ru.java.crm.mapper.CustomerMapper;
import ru.java.crm.repository.CustomerRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    // Получить всех клиентов
    public List<CustomerDto> getAllCustomers() {
        log.info("Get all customers");
        return customerRepository.findAll().stream()
                .map(customerMapper::toDto)
                .toList();
    }

    // Создать клиента
    public CustomerDto createCustomer(CustomerDto customer) {
        log.info("Create customer: {}", customer);

        // Проверка на уникальность email
        if (customerRepository.existsByEmail(customer.getEmail())) {
            log.error("Customer with email {} already exists", customer.getEmail());
            throw new EntityExistsException("Customer with this email already exists");
        }
        final var entity = customerMapper.toEntity(customer);
        final var savedCustomer = customerRepository.save(entity);
        return customerMapper.toDto(savedCustomer);
    }

    // Получить клиента по ID
    public CustomerDto getCustomerById(Long customerId) {
        log.info("Get customer by ID: {}", customerId);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        return customerMapper.toDto(customer);
    }

    // Обновить информацию о клиенте
    public CustomerDto updateCustomer(Long customerId, CustomerDto customer) {
        log.info("Update customer: {}", customerId);
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        // Обновляем поля существующего клиента
        existingCustomer.setName(customer.getName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhone(customer.getPhone());

        final var updatedCustomer = customerRepository.save(existingCustomer);
        return customerMapper.toDto(updatedCustomer);
    }

    // Удалить клиента
    public void deleteCustomer(Long customerId) {
        log.info("Delete customer: {}", customerId);
        if (!customerRepository.existsById(customerId)) {
            log.error("Customer not found: {}", customerId);
            throw new EntityNotFoundException("Customer not found");
        }
        customerRepository.deleteById(customerId);
    }
}
