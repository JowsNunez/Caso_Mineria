package com.itson.edu.mx.caso_mineria.semaforo;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class SemaforoSender {
    public static final String EXCHANGE_NAME = "envio.semaforo";
    public static final String ROUTING_KEY = "semaforos";

    private static final String QUEUE_NAME = "semaforo";
    private static final boolean IS_DURABLE_QUEUE = true;

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
}
