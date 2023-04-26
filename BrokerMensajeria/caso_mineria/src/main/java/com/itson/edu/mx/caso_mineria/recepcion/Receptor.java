package com.itson.edu.mx.caso_mineria.recepcion;

import java.util.logging.Logger;

import com.google.gson.Gson;
import com.itson.edu.mx.caso_mineria.semaforo.Semaforo;
import com.itson.edu.mx.caso_mineria.semaforo.ThreadSemaforos;

import java.util.logging.Level;

/** Clase encargada de imprimir el mensaje recibido. */
public class Receptor  {
    private static final Logger LOG = Logger.getLogger(Receptor.class.toString());
    public static final String RECEIVE_METHOD_NAME = "receiveMessage";

    public static void receiveMessage(byte[] message) {
        String msg = new String(message);
        
        Gson gson = new Gson();

        Semaforo semaforo = gson.fromJson(msg, Semaforo.class);
        ThreadSemaforos.update(semaforo);
       
        LOG.log(Level.INFO, "Mensage Recibido {0}", semaforo);
    
      
    }

  
}