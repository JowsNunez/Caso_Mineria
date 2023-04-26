package com.itson.edu.mx.caso_mineria;

// import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.itson.edu.mx.caso_mineria.semaforo.ThreadSemaforos;

@SpringBootApplication
public class CasoMineriaApplication implements CommandLineRunner {
	//private static final String MESSAGE = "Semáforo emisor: ..., Estado: ..., Ubicación: ...";
	/***
	 * Se incluye el bean RabbitTemplate.
	 */
	@Autowired
	ThreadSemaforos threadSemaforos;
	//RabbitTemplate rabbitTemplate;

	public static void main(String[] args) {
		SpringApplication.run(CasoMineriaApplication.class, args);
	}

	@Override
	public void run(String... args) throws InterruptedException {
		threadSemaforos.startThreads();
		// System.out.println("[Application] Enviando el mensaje\"" + MESSAGE + "\"...");
		// // convertAndSend -> Indicamos que queremos enviar el mensaje MESSAGE a las
		// // colas que tengan un enlace al exchange con clave ROUTING_KEY
		// rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, RabbitMqConfig.ROUTING_KEY, MESSAGE);
	}

}
