package ru.java.crm.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.java.crm.dto.DealDto;
import ru.java.crm.entity.Deal;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-11T23:28:39+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class DealMapperImpl implements DealMapper {

    @Override
    public DealDto toDto(Deal deal) {
        if ( deal == null ) {
            return null;
        }

        DealDto.DealDtoBuilder dealDto = DealDto.builder();

        dealDto.amount( deal.getAmount() );
        dealDto.status( deal.getStatus() );

        return dealDto.build();
    }

    @Override
    public Deal toEntity(DealDto dto) {
        if ( dto == null ) {
            return null;
        }

        Deal.DealBuilder deal = Deal.builder();

        deal.amount( dto.getAmount() );
        deal.status( dto.getStatus() );

        return deal.build();
    }
}
