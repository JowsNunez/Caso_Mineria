package com.itson.edu.mx.caso_mineria.recepcion;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ReceptorConfig {

    private static String QUEUE_NAME="semaforo.cambios";
    private static String EXCHANGE_NAME="envio.semaforo";
    private static String ROUTING_KEY = "semaforo.*";


    @Bean
    Queue queueReceptor() {
        return new Queue(QUEUE_NAME,false);
    }

    @Bean
    TopicExchange topicExchangeReceptor() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding bindingReceptor(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    SimpleMessageListenerContainer containerReceptor(ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        final SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    Receptor receiver() {
        return new Receptor();
    }

    /***
     * Adaptador que indica quén recibe el mensaje y qué método lo procesa.
     * 
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(Receptor receiver) {
        return new MessageListenerAdapter(receiver, Receptor.RECEIVE_METHOD_NAME);
    }

}
