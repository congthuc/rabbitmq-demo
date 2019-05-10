package com.congthuc.rabbitmqdemo.sender;

import com.congthuc.rabbitmqdemo.config.RabbitConfig;
import com.congthuc.rabbitmqdemo.dto.Letter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: pct
 * 5/9/2019
 */

@Service
public class LetterMessageSender {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public LetterMessageSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendLetter(Letter letter) {
        //this.rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_ORDERS, order);
        try {
            String orderJson = objectMapper.writeValueAsString(letter);
            Message message = MessageBuilder
                    .withBody(orderJson.getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .build();

            this.rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_LETTERS, RabbitConfig.QUEUE_LETTERS_ROUTE_KEY,
                    message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
