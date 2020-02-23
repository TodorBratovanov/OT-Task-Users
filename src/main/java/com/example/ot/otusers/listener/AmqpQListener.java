package com.example.ot.otusers.listener;

import com.example.ot.otusers.model.UserDTO;
import com.example.ot.otusers.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmqpQListener {

    private final UserService userService;

    @RabbitListener(queues = "amqpQueue")
    public void listen(List<UserDTO> users) {
        log.info("AMQP: Received message with {} users", users.size());
        List<UserDTO> created = userService.create(users);
        created.forEach(user -> log.info("AMQP: Created user in bulk: {} {}", user.getFirstName(), user.getLastName()));
    }

}
