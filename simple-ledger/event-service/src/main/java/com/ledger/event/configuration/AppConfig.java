package com.ledger.event.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@Configuration
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@EnableJpaAuditing
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setPreferNestedProperties(false);

        return modelMapper;
    }

    @Bean
    public TopicExchange eventsExchange() {
        return new TopicExchange("events-exchange", true, true);
    }

    @Bean
    public Queue eventServiceQueue() {
        return new Queue("event-service-queue", true);
    }

    @Bean
    public Binding eventBinding(Queue eventServiceQueue, TopicExchange eventsExchange) {
        return BindingBuilder.bind(eventServiceQueue)
                .to(eventsExchange)
                .with("transaction.event");
    }
}
