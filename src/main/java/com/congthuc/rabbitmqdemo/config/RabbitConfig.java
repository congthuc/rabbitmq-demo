package com.congthuc.rabbitmqdemo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

/**
 * Author: pct
 * 5/9/2019
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class RabbitConfig implements RabbitListenerConfigurer {

    public static final String EXCHANGE_ORDERS = "orders-exchange";
    public static final String QUEUE_ORDERS = "orders-queue";
    public static final String QUEUE_ORDERS_ROUTE_KEY = "x-orders-queue";

    public static final String EXCHANGE_LETTERS = "letters-exchange";
    public static final String QUEUE_LETTERS = "letters-queue";
    public static final String QUEUE_LETTERS_ROUTE_KEY = "x-letters-queue";

    public static final String QUEUE_DEAD_ORDERS = "dead-orders-queue";
    public static final String QUEUE_DEAD_LETTERS = "dead-letter-queue";

    @Bean
    Queue ordersQueue() {
        return QueueBuilder.durable(QUEUE_ORDERS)
                .withArgument("x-max-length", 5000)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_DEAD_ORDERS)
                .withArgument("x-message-ttl", 60000)
                .build();
    }

    @Bean
    Queue lettersQueue() {
        return QueueBuilder.durable(QUEUE_LETTERS)
                .withArgument("x-max-length", 5000)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_DEAD_LETTERS)
                .withArgument("x-message-ttl", 60000)
                .build();
    }

    @Bean
    Queue deadOrdersQueue() {
        return QueueBuilder.durable(QUEUE_DEAD_ORDERS).build();
    }

    @Bean
    Queue deadLettersQueue() {
        return QueueBuilder.durable(QUEUE_DEAD_LETTERS).build();
    }

    @Bean
    Exchange ordersExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_ORDERS).build();
    }

    @Bean
    Exchange lettersExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_LETTERS).build();
    }

    @Bean
    Binding ordersBinding(Queue ordersQueue, TopicExchange ordersExchange) {
        return BindingBuilder.bind(ordersQueue).to(ordersExchange).with(QUEUE_ORDERS_ROUTE_KEY);
    }

    @Bean
    Binding lettersBinding(Queue lettersQueue, TopicExchange lettersExchange) {
        return BindingBuilder.bind(lettersQueue).to(lettersExchange).with(QUEUE_LETTERS_ROUTE_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }
}