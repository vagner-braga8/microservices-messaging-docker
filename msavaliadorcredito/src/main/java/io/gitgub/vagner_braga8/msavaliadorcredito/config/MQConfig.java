package io.gitgub.vagner_braga8.msavaliadorcredito.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    private static final Logger log = LoggerFactory.getLogger(MQConfig.class);

    @Value("${mq.queues.emissao-cartoes}")
    private String emissaoCartoesFila;

    @Bean
    public Queue queueEmissaoCartoes() {
        log.info("Criando bean da fila: {}", emissaoCartoesFila);
        return new Queue(emissaoCartoesFila, true);
    }

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Bean
    public ApplicationRunner runner(Queue queueEmissaoCartoes) {
        return args -> {
            log.info("Declarando fila: {}", queueEmissaoCartoes.getName());
            amqpAdmin.declareQueue(queueEmissaoCartoes);
        };
    }
}