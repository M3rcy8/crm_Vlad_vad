package ru.java.crm.mapper;

import org.mapstruct.Mapper;
import ru.java.crm.dto.UserDto;
import ru.java.crm.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto dto);
}
