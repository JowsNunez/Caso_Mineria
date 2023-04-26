package com.itson.edu.mx.caso_mineria.semaforo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class RunnableSemaforo implements Runnable {

    private Semaforo semaforo;
    private boolean changed;

    private static final Logger LOG = Logger.getLogger(RunnableSemaforo.class.toString());

    RabbitTemplate rabbitTemplate;

    public RunnableSemaforo(Semaforo semaforo,
            RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.semaforo = semaforo;
        this.changed=false;
    }

    @Override
    public void run() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        Runnable task = () -> {
            try {

                if(!changed){
                    if (this.semaforo.getEstado() == Estado.VERDE) {
                        this.semaforo.setEstado(Estado.AMARILLO);
                       
                        changed=true;
                    }
                }
                if(!changed){
                    if (this.semaforo.getEstado() == Estado.AMARILLO) {
                        this.semaforo.setEstado(Estado.ROJO);
                       
                        changed=true;
                    }
                }
                if(!changed){
                    if (this.semaforo.getEstado() == Estado.ROJO) {
                        this.semaforo.setEstado(Estado.VERDE);
                       
                        changed=true;
                    }
                }
                    
               
                

                Gson gson = new Gson();
                String message = gson.toJson(this.semaforo, Semaforo.class);
                LOG.log(Level.INFO, ">> SEND {0}", message);

                rabbitTemplate.convertAndSend(SemaforoSender.EXCHANGE_NAME, SemaforoSender.ROUTING_KEY, message);
                changed=false;
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, "Error al enviar el estado del semáforo", ex);
            }
        };
        executorService.scheduleAtFixedRate(task, 0, semaforo.getTiempo(), TimeUnit.MILLISECONDS);
    }

    public void update(Semaforo semaforo){
        if (this.semaforo.getIdSemaforo().equals(semaforo.getIdSemaforo())) {
            this.semaforo.setEstado(semaforo.getEstado());
            this.semaforo.setTiempo(semaforo.getTiempo());
            Gson gson = new Gson();
                String message = gson.toJson(this.semaforo, Semaforo.class);
          this.rabbitTemplate.convertAndSend(SemaforoSender.EXCHANGE_NAME, SemaforoSender.ROUTING_KEY, message);
        }
    }


  
    
}
