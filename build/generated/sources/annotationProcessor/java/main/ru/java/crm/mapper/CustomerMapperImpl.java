package ru.java.crm.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.java.crm.dto.CustomerDto;
import ru.java.crm.entity.Customer;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-12T00:25:50+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public CustomerDto toDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDto.CustomerDtoBuilder customerDto = CustomerDto.builder();

        customerDto.name( customer.getName() );
        customerDto.email( customer.getEmail() );
        customerDto.phone( customer.getPhone() );

        return customerDto.build();
    }

    @Override
    public Customer toEntity(CustomerDto dto) {
        if ( dto == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.name( dto.getName() );
        customer.email( dto.getEmail() );
        customer.phone( dto.getPhone() );

        return customer.build();
    }
}
