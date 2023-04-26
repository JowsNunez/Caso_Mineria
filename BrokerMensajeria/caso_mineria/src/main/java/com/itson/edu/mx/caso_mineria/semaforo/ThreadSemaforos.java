package com.itson.edu.mx.caso_mineria.semaforo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThreadSemaforos {

    private static final Map<String, RunnableSemaforo> threads = new HashMap<>();
    ExecutorService executor = Executors.newFixedThreadPool(10);
    @Autowired
    RabbitTemplate rabbitTemplate;

    private static final Logger LOG = Logger.getLogger(ThreadSemaforos.class.toString());

    public ThreadSemaforos() {

    }

    public void startThreads() {
        Persist.iniciar();
        Persist.getSemaforos().forEach(e -> {

            RunnableSemaforo runnableS = new RunnableSemaforo(e, rabbitTemplate);
            Thread th = new Thread(runnableS);
            threads.put(e.getIdSemaforo(), runnableS);
            runnableS.update(e);
            executor.execute(th);

            LOG.log(Level.INFO, "Se agrego a la lista de Threads {0}", th);

        });
        ;

        if (threads.size() > 0) {
            LOG.log(Level.INFO, "Se agregaron los threads");
        } else {
            LOG.log(Level.WARNING, "No se agregaron los threads");

        }
    }

    public static void update(Semaforo semaforo) {
        RunnableSemaforo sem = threads.get(semaforo.getIdSemaforo());
        sem.update(semaforo);
    }

}
