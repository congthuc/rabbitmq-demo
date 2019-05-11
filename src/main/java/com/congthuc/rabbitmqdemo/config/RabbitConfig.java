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

    public static final String EXCHANGE_NOTIFICATION = "notifications-exchange";

    // queue notification for all employee in Netc VN
    public static final String QUEUE_NOTIFICATION_NETC_VN_ALL = "notification-all-netc-vn-queue";
    public static final String QUEUE_NOTIFICATION_NETC_VN_ALL_ROUTE_KEY = "netc.vn.*";

    // queue notification for all manager in Netc VN
    public static final String QUEUE_NOTIFICATION_NETC_VN_MANAGER = "notification-manager-netc-vn-queue";
    public static final String QUEUE_NOTIFICATION_NETC_VN_MANAGER_ROUTE_KEY = "netc.vn.manager.#";

    // queue notification for all consultant in Netc VN
    public static final String QUEUE_NOTIFICATION_NETC_VN_CONSULTANT = "notification-consultant-netc-vn-queue";
    public static final String QUEUE_NOTIFICATION_NETC_VN_CONSULTANT_ROUTE_KEY = "netc.vn.consultant.#";

    // queue notification for all dev-ops in Netc VN
    public static final String QUEUE_NOTIFICATION_NETC_VN_DEVOPS = "notification-devops-netc-vn-queue";
    public static final String QUEUE_NOTIFICATION_NETC_VN_DEVOPS_ROUTE_KEY = "netc.vn.dev-ops.#";

    public static final String QUEUE_DEAD_NOTIFICATIONS = "dead-notifications-queue";


    // all the beans for order message
    @Bean
    Exchange ordersExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_ORDERS).build();
    }

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
    Binding ordersBinding(Queue ordersQueue, Exchange ordersExchange) {
        return BindingBuilder.bind(ordersQueue).to((TopicExchange)ordersExchange).with(QUEUE_ORDERS_ROUTE_KEY);
    }

    @Bean
    Queue deadOrdersQueue() {
        return QueueBuilder.durable(QUEUE_DEAD_ORDERS).build();
    }


    // all the beans for letter message
    @Bean
    Exchange lettersExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_LETTERS).build();
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
    Binding lettersBinding(Queue lettersQueue, Exchange lettersExchange) {
        return BindingBuilder.bind(lettersQueue).to((TopicExchange)lettersExchange).with(QUEUE_LETTERS_ROUTE_KEY);
    }

    @Bean
    Queue deadLettersQueue() {
        return QueueBuilder.durable(QUEUE_DEAD_LETTERS).build();
    }

    // all the beans for notification message
    @Bean
    Exchange notificationsExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NOTIFICATION).build();
    }

    @Bean
    Queue deadNotificationsQueue() {
        return QueueBuilder.durable(QUEUE_DEAD_NOTIFICATIONS).build();
    }

    @Bean
    Queue allEmployeesQueue() {
        return QueueBuilder.durable(QUEUE_NOTIFICATION_NETC_VN_ALL)
                .withArgument("x-max-length", 5000)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_DEAD_NOTIFICATIONS)
                .withArgument("x-message-ttl", 60 * 60 * 1000)
                .build();
    }
    @Bean
    Binding allEmployeesBinding(Queue allEmployeesQueue, Exchange notificationsExchange) {
        return BindingBuilder.bind(allEmployeesQueue).to((TopicExchange)notificationsExchange).with(QUEUE_NOTIFICATION_NETC_VN_ALL_ROUTE_KEY);
    }

    @Bean
    Queue managersQueue() {
        return QueueBuilder.durable(QUEUE_NOTIFICATION_NETC_VN_MANAGER)
                .withArgument("x-max-length", 5000)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_DEAD_NOTIFICATIONS)
                .withArgument("x-message-ttl", 60 * 60 * 1000)
                .build();
    }
    @Bean
    Binding managersBinding(Queue managersQueue, Exchange notificationsExchange) {
        return BindingBuilder.bind(managersQueue).to((TopicExchange)notificationsExchange).with(QUEUE_NOTIFICATION_NETC_VN_MANAGER_ROUTE_KEY);
    }

    @Bean
    Queue consultantsQueue() {
        return QueueBuilder.durable(QUEUE_NOTIFICATION_NETC_VN_CONSULTANT)
                .withArgument("x-max-length", 5000)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_DEAD_NOTIFICATIONS)
                .withArgument("x-message-ttl", 60 * 60 * 1000)
                .build();
    }
    @Bean
    Binding consultantsBinding(Queue consultantsQueue, Exchange notificationsExchange) {
        return BindingBuilder.bind(consultantsQueue).to((TopicExchange)notificationsExchange).with(QUEUE_NOTIFICATION_NETC_VN_CONSULTANT_ROUTE_KEY);
    }

    @Bean
    Queue devopsesQueue() {
        return QueueBuilder.durable(QUEUE_NOTIFICATION_NETC_VN_DEVOPS)
                .withArgument("x-max-length", 5000)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_DEAD_NOTIFICATIONS)
                .withArgument("x-message-ttl", 60 * 60 * 1000)
                .build();
    }
    @Bean
    Binding devopsesBinding(Queue devopsesQueue, Exchange notificationsExchange) {
        return BindingBuilder.bind(devopsesQueue).to((TopicExchange)notificationsExchange).with(QUEUE_NOTIFICATION_NETC_VN_DEVOPS_ROUTE_KEY);
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