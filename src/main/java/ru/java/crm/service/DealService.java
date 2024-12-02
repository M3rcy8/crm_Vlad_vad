package ru.java.crm.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.java.crm.configuration.RabbitMQProperties;
import ru.java.crm.dto.DealDto;
import ru.java.crm.entity.Deal;
import ru.java.crm.mapper.DealMapper;
import ru.java.crm.repository.DealRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DealService {

    private final DealRepository dealRepository;
    private final DealMapper dealMapper;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties properties;

    // Получить все сделки
    public List<DealDto> getAllDeals() {
        log.info("Get all deals");
        return dealRepository.findAll().stream()
                .map(dealMapper::toDto)
                .toList();
    }

    // Создать сделку
    public DealDto createDeal(DealDto deal) {
        log.info("Create deal: {}", deal);
        final var entity = dealMapper.toEntity(deal);
        final var savedDeal = dealRepository.save(entity);
        final var dealDto = dealMapper.toDto(savedDeal);
        rabbitTemplate.convertAndSend(
                properties.getProduce().getDeal().getExchange(),
                properties.getProduce().getDeal().getRoutingKey(),
                dealDto
        );
        return dealDto;
    }

    // Получить сделки по клиенту
    public List<DealDto> getDealsByCustomer(Long customerId) {
        log.info("Get deals for customer: {}", customerId);
        return dealRepository.findByCustomerId(customerId).stream()
                .map(dealMapper::toDto)
                .toList();
    }

    // Обновить статус сделки
    public DealDto updateDealStatus(Long dealId, String newStatus) {
        log.info("Update status of deal: {} to {}", dealId, newStatus);
        Deal deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new EntityNotFoundException("Deal not found"));

        deal.setStatus(newStatus);
        Deal savedDeal = dealRepository.save(deal);
        final var dealDto = dealMapper.toDto(savedDeal);
        rabbitTemplate.convertAndSend(
                properties.getProduce().getDealStatusUpdated().getExchange(),
                properties.getProduce().getDealStatusUpdated().getRoutingKey(),
                dealDto
        );
        return dealDto;
    }

    // Удалить сделку
    public void deleteDeal(Long dealId) {
        log.info("Delete deal: {}", dealId);
        if (!dealRepository.existsById(dealId)) {
            log.error("Deal not found: {}", dealId);
            throw new EntityNotFoundException("Deal not found");
        }
        dealRepository.deleteById(dealId);
    }
}
