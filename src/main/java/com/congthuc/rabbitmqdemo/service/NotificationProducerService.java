package com.congthuc.rabbitmqdemo.service;

import com.congthuc.rabbitmqdemo.config.RabbitConfig;
import com.congthuc.rabbitmqdemo.dto.Letter;
import com.congthuc.rabbitmqdemo.utils.RouteKeyUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * Author: pct
 * 5/11/2019
 */

@Service
public class NotificationProducerService {
    static final Logger logger = LoggerFactory.getLogger(NotificationProducerService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void produce() {
        for (String routingKey : RouteKeyUtils.routeKeys) {
            logger.info(" sending the notified message with routing key {}", routingKey);

            try {
                Letter letter = new Letter(UUID.randomUUID().toString(), "No-reply", "This is the auto message",
                        new ArrayList <String>(Arrays.asList("congthuc.uit@gmail.com")));

                String orderJson = objectMapper.writeValueAsString(letter);
                Message message = MessageBuilder
                        .withBody(orderJson.getBytes())
                        .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                        .build();
                rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NOTIFICATION, routingKey, message);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }
    }
}
