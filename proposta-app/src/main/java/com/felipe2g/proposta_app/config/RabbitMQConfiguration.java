package com.felipe2g.proposta_app.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbitmq.propostapendente.exchange}")
    private String exchangePropostaPendente;

    @Value("${rabbitmq.propostaconcluida.exchange}")
    private String exchangePropostaConcluida;

    public static final String PROPOSTA_PENDENTE_MS_ANALISE_CREDITO_QUEUE = "proposta-pendente.ms-analise-credito";
    public static final String PROPOSTA_PENDENTE_MS_NOTIFICACAO_QUEUE = "proposta-pendente.ms-notificacao";
    public static final String PROPOSTA_CONCLUIDA_MS_PROPOSTA_QUEUE = "proposta-concluida.ms-proposta";
    public static final String PROPOSTA_CONCLUIDA_MS_NOTIFICACAO_QUEUE = "proposta-concluida.ms-notificacao";
    public static final String PROPOSTA_PENDENTE_DLQ = "proposta-pendente.dlq";
    public static final String PROPOSTA_PENDENTE_DLX = "proposta-pendente-dlx.ex";

    @Bean
    public Queue criarFilaPropostaPendenteMsAnaliseCredito() {
        return QueueBuilder.durable(PROPOSTA_PENDENTE_MS_ANALISE_CREDITO_QUEUE)
                .deadLetterExchange(PROPOSTA_PENDENTE_DLX)
                .build();
    }

    @Bean
    public Queue criarFilaPropostaPendenteMsNotificacao() {
        return new Queue(PROPOSTA_PENDENTE_MS_NOTIFICACAO_QUEUE);
    }

    @Bean
    public Queue criarFilaPropostaConcluidaMsProposta() {
        return new Queue(PROPOSTA_CONCLUIDA_MS_PROPOSTA_QUEUE);
    }

    @Bean
    public Queue criarFilaPropostaConcluidaMsNotificacao() {
        return new Queue(PROPOSTA_CONCLUIDA_MS_NOTIFICACAO_QUEUE);
    }

    @Bean
    public Queue criarFilaPropostaPendenteDlq() {
        return new Queue(PROPOSTA_PENDENTE_DLQ);
    }

    @Bean
    public FanoutExchange deadLetterExchange() {
        return ExchangeBuilder.fanoutExchange(PROPOSTA_PENDENTE_DLX).build();
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(criarFilaPropostaPendenteDlq()).to(deadLetterExchange());
    }

    @Bean
    public RabbitAdmin criarRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializarAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public FanoutExchange criarFanoutExchangePropostaPendente() {
        return ExchangeBuilder.fanoutExchange(exchangePropostaPendente).build();
    }

    @Bean
    public FanoutExchange criarFanoutExchangePropostaConcluida() {
        return ExchangeBuilder.fanoutExchange(exchangePropostaConcluida).build();
    }

    @Bean
    public Binding criarBindingPropostaPendenteMsAnaliseCredito() {
        return BindingBuilder.bind(criarFilaPropostaPendenteMsAnaliseCredito()).to(criarFanoutExchangePropostaPendente());
    }

    @Bean
    public Binding criarBindingPropostaConcluidaMsPropostaApp() {
        return BindingBuilder.bind(criarFilaPropostaConcluidaMsProposta()).to(criarFanoutExchangePropostaConcluida());
    }

    @Bean
    public Binding criarBindingPropostaConcluidaMsNotificacao() {
        return BindingBuilder.bind(criarFilaPropostaConcluidaMsNotificacao()).to(criarFanoutExchangePropostaConcluida());
    }

    @Bean
    public Binding criarBindingPropostaPendenteMsNotificacao() {
        return BindingBuilder.bind(criarFilaPropostaPendenteMsNotificacao()).to(criarFanoutExchangePropostaPendente());
    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());

        return rabbitTemplate;
    }
}
