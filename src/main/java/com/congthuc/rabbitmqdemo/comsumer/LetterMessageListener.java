package com.congthuc.rabbitmqdemo.comsumer;

import com.congthuc.rabbitmqdemo.config.RabbitConfig;
import com.congthuc.rabbitmqdemo.dto.Letter;
import com.congthuc.rabbitmqdemo.dto.Order;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Author: pct
 * 5/10/2019
 */

@Component
public class LetterMessageListener {

    static final Logger logger = LoggerFactory.getLogger(LetterMessageListener.class);

    @RabbitListener(queues = RabbitConfig.QUEUE_LETTERS)
    public void processLetter(Letter letter) {
        if(StringUtils.isEmpty(letter.getSubject())) {
            throw new AmqpRejectAndDontRequeueException("Could not process the letter missed the subject");
        }
        logger.info("Letter Received: " + letter);
    }
}
