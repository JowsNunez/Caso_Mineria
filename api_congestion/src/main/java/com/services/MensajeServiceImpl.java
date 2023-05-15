package com.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mensajes.Mensaje;
import com.mensajes.RabbitConfig;

@Service
public class MensajeServiceImpl implements IMensajesService {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public void send(Mensaje mensaje) {
       
        Gson gson = new Gson();

        String msj =gson.toJson(mensaje,Mensaje.class);

        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, msj);
    }
    
}
