package com.authservice.authservice.consumer;

import com.authservice.authservice.entity.dto.UserDto;
import com.authservice.authservice.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private AccountService accountService;

    @KafkaListener(topics = "${spring.kafka.topic.userCreated}")
    public void receive(UserDto payload) {
        LOGGER.info("received payload='{}'", payload);
        accountService.registerUser(payload);
        latch.countDown();
    }
}
