package ru.java.crm.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.java.crm.dto.UserDto;
import ru.java.crm.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-12T00:14:16+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.username( user.getUsername() );
        userDto.password( user.getPassword() );
        userDto.role( user.getRole() );

        return userDto.build();
    }

    @Override
    public User toEntity(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( dto.getUsername() );
        user.password( dto.getPassword() );
        user.role( dto.getRole() );

        return user.build();
    }
}
