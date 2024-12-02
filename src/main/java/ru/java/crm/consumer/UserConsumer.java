package ru.java.crm.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.java.crm.dto.UserDto;
import ru.java.crm.mapper.UserMapper;
import ru.java.crm.repository.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserConsumer {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @RabbitListener(queues = "${crm.messaging.consume.user.queue}")
    public void handleUserCreated(UserDto userDto) {
        System.out.println("User created: " + userDto);
        final var entity = userMapper.toEntity(userDto);
        userRepository.save(entity);
    }
}
