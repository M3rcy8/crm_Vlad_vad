package ru.java.crm.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.java.crm.configuration.RabbitMQProperties;
import ru.java.crm.dto.UserDto;
import ru.java.crm.entity.User;
import ru.java.crm.mapper.UserMapper;
import ru.java.crm.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties properties;

    // Получить всех пользователей
    public List<UserDto> getAllUsers() {
        log.info("Get all users");
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    // Создать нового пользователя
    public UserDto createUser(UserDto user) {
        log.info("Create user: {}", user);
//        final var entity = userMapper.toEntity(user);
//        final var savedUser = userRepository.save(entity);
//        final var userDto = userMapper.toDto(savedUser);
        rabbitTemplate.convertAndSend(
                properties.getProduce().getUser().getExchange(),
                properties.getProduce().getUser().getRoutingKey(),
                user
        );
        return user;
    }

    // Получить пользователя по ID
    public UserDto getUserById(Long userId) {
        log.info("Get user by ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return userMapper.toDto(user);
    }

    // Обновить пользователя
    public UserDto updateUser(Long userId, UserDto updatedUser) {
        log.info("Update user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword()); // При необходимости применить хеширование пароля
        user.setRole(updatedUser.getRole());

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    // Удалить пользователя
    public void deleteUser(Long userId) {
        log.info("Delete user with ID: {}", userId);
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(userId);
    }
}
