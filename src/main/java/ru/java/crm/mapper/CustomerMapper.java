package ru.java.crm.mapper;

import org.mapstruct.Mapper;
import ru.java.crm.dto.CustomerDto;
import ru.java.crm.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDto toDto(Customer customer);

    Customer toEntity(CustomerDto dto);
}
