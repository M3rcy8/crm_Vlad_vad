package ru.java.crm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.java.crm.dto.DealDto;
import ru.java.crm.service.DealService;

import java.util.List;

@RestController
@RequestMapping("/api/deals")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    // Получить все сделки
    @GetMapping
    public List<DealDto> getAllDeals() {
        return dealService.getAllDeals();
    }

    // Создать сделку
    @PostMapping
    public DealDto createDeal(@RequestBody DealDto deal) {
        return dealService.createDeal(deal);
    }

    // Получить сделки по клиенту
    @GetMapping("/customer/{customerId}")
    public List<DealDto> getDealsByCustomer(@PathVariable Long customerId) {
        return dealService.getDealsByCustomer(customerId);
    }

    // Обновить статус сделки
    @PutMapping("/{dealId}/status")
    public DealDto updateDealStatus(@PathVariable Long dealId, @RequestParam String status) {
        return dealService.updateDealStatus(dealId, status);
    }

    // Удалить сделку
    @DeleteMapping("/{dealId}")
    public ResponseEntity<Void> deleteDeal(@PathVariable Long dealId) {
        dealService.deleteDeal(dealId);
        return ResponseEntity.noContent().build(); // Возвращаем статус 204 No Content
    }
}
