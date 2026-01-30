package com.pandastudios.librarymanagement.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.amqp.core.Binding;

@Configuration
public class RabbitConfig {

    public static final String BORROW_EXCHANGE = "borrow-exchange";
    public static final String BORROW_ROUTING_KEY = "borrow-record-created";

    public static final String RETURN_EXCHANGE = "return-exchange";
    public static final String RETURN_ROUTING_KEY = "return-record-created";

    @Bean
    public TopicExchange borrowExchange() {
        return new TopicExchange(BORROW_EXCHANGE, true, false);
    }

    @Bean
    public TopicExchange returnExchange() {
        return new TopicExchange(RETURN_EXCHANGE, true, false);
    }

    @Bean
    public Queue calenderQueue() {
        return new Queue("borrow-calender-queue", true);
    }

    @Bean
    public Queue borrowLoggingQueue() {
        return new Queue("borrow-logging-queue", true);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue("return-notification-queue", true);
    }

    @Bean
    public Queue returnLoggingQueue() {
        return new Queue("return-logging-queue", true);
    }

    // Bindings for Borrow
    @Bean
    public Binding bindCalendarQueueToBorrowExchange(Queue calenderQueue, TopicExchange borrowExchange) {
        return BindingBuilder.bind(calenderQueue).to(borrowExchange).with(BORROW_ROUTING_KEY);
    }

    @Bean
    public Binding bindBorrowLoggingQueueToBorrowExchange(Queue borrowLoggingQueue, TopicExchange borrowExchange) {
        return BindingBuilder.bind(borrowLoggingQueue).to(borrowExchange).with(BORROW_ROUTING_KEY);
    }

    // Bindings for Return
    @Bean
    public Binding bindNotificationQueueToReturnExchange(Queue notificationQueue, TopicExchange returnExchange) {
        return BindingBuilder.bind(notificationQueue).to(returnExchange).with(RETURN_ROUTING_KEY);
    }

    @Bean
    public Binding bindReturnLoggingQueueToReturnExchange(Queue returnLoggingQueue, TopicExchange returnExchange) {
        return BindingBuilder.bind(returnLoggingQueue).to(returnExchange).with(RETURN_ROUTING_KEY);
    }


    @SuppressWarnings("removal")
    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return new Jackson2JsonMessageConverter(mapper);
    }


}
