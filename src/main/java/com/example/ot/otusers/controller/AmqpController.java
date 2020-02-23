package com.example.ot.otusers.controller;

import com.example.ot.otusers.model.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/amqp")
@RequiredArgsConstructor
@Slf4j
public class AmqpController {

    private final RabbitTemplate rabbitTemplate;

    @PostMapping("/users")
    public ResponseEntity<List<UserDTO>> insertBulk(@RequestBody @Valid @NotNull List<UserDTO> users) {
        rabbitTemplate.convertAndSend("amqpQueue", users);
        log.info("AMQP: Bulk with {} users send to queue", users.size());

        return ResponseEntity.ok().build();
    }

}
