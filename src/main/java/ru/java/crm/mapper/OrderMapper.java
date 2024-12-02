package ru.java.crm.mapper;

import org.mapstruct.Mapper;
import ru.java.crm.dto.OrderDto;
import ru.java.crm.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);

    Order toEntity(OrderDto dto);
}
