package com.bartergrid.chatservice.config.axon;

import com.bartergrid.chatservice.command.aggregate.ChatRoomAggregate;
import com.bartergrid.core.service.axon.BarterGridRepositoryConfigurer;
import com.bartergrid.core.service.axon.recovery.BarterGridMonitorAndRecoveryCommandGateway;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.annotation.ParameterResolverFactory;
import org.axonframework.modelling.command.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonChatConfig {

    @Value("${axon.eventhandling.processors.group.name}")
    private String processorGroupName;

    @Value("${axon.repo.lock.pessimistic:true}")
    private boolean usePessimisticLocking;

    @Bean
    public Repository<ChatRoomAggregate> eventStoreRepository(EventStore eventStore,
                                                              ParameterResolverFactory factory) {
        return BarterGridRepositoryConfigurer.defaultRepository(
                ChatRoomAggregate.class,
                eventStore,
                factory,
                usePessimisticLocking
        );
    }

    @Bean
    public CommandGateway commandGateway(CommandBus commandBus, EventProcessingConfiguration config) {
        String group = processorGroupName; // or from properties

        return new BarterGridMonitorAndRecoveryCommandGateway(
                BarterGridMonitorAndRecoveryCommandGateway.builder().commandBus(commandBus),
                config,
                group
        );
    }
}