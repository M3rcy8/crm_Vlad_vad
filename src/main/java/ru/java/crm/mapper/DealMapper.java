package ru.java.crm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.java.crm.dto.DealDto;
import ru.java.crm.entity.Deal;

@Mapper(componentModel = "spring")
public interface DealMapper {
    DealDto toDto(Deal deal);

    Deal toEntity(DealDto dto);
}
