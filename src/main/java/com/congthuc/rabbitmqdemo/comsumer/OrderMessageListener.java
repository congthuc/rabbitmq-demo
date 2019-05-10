package com.congthuc.rabbitmqdemo.comsumer;

import com.congthuc.rabbitmqdemo.config.RabbitConfig;
import com.congthuc.rabbitmqdemo.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Author: pct
 * 5/9/2019
 */

@Component
public class OrderMessageListener {
    static final Logger logger = LoggerFactory.getLogger(OrderMessageListener.class);

    @RabbitListener(queues = RabbitConfig.QUEUE_ORDERS)
    public void processOrder(Order order) throws Exception {
        if(order.getAmount() > 2000) {
            throw new AmqpRejectAndDontRequeueException("Could not process the order that greater than 3000$");
        }
        logger.info("Order Received: " + order);
    }
}
