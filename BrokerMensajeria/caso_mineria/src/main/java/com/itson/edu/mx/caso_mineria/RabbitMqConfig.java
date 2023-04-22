package com.itson.edu.mx.caso_mineria;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * Indica a Spring que contendrá definiciones de Beans.
 */
@Configuration
public class RabbitMqConfig {
    public static final String EXCHANGE_NAME = "exchange_name";
    public static final String ROUTING_KEY = "routing_key";

    private static final String QUEUE_NAME = "queue_name";
    private static final boolean IS_DURABLE_QUEUE = false;

    /***
     * Crea una cola a la que le otorga un nombre y define su durabilidad.
     * 
     * @return
     */
    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, IS_DURABLE_QUEUE);
    }

    /***
     * Crea un exchange de tipo topic y le otorga un nombre.
     * 
     * @return
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    /***
     * Enlaza una cola con un exchange de tipo topic. with para definir la clave de
     * enlace.
     * 
     * @return
     */
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    /***
     * Contenedor en el que se indican quiénes son los consumidores de las colas.
     * 
     * @return
     */
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        final SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    /***
     * Receptor del mensaje
     * @return
     */
    @Bean
    Receptor receiver() {
        return new Receptor();
    }

    /***
     * Adaptador que indica quén recibe el mensaje y qué método lo procesa.
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(Receptor receiver) {
        return new MessageListenerAdapter(receiver, Receptor.RECEIVE_METHOD_NAME);
    }
}
