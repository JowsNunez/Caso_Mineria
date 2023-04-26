package com.itson.edu.mx.caso_mineria.semaforo;

import java.util.ArrayList;
import java.util.List;

public class Persist {
    
    private static final List<Semaforo> SEMAFOROS=new ArrayList<>();



    public static void iniciar(){


        SEMAFOROS.add(new Semaforo("semaforo_1", Estado.VERDE, new Ubicacion( 30.96719745678408, -110.32206220589997), 950));
        SEMAFOROS.add(new Semaforo("semaforo_2", Estado.VERDE, new Ubicacion( 30.963636336478395, -110.32739755907858), 950));
        SEMAFOROS.add(new Semaforo("semaforo_3", Estado.VERDE, new Ubicacion( 30.96711660132356, -110.32246927576035 ), 950));
        SEMAFOROS.add(new Semaforo("semaforo_4", Estado.VERDE, new Ubicacion( 30.964567, -110.322097 ), 950));
        SEMAFOROS.add(new Semaforo("semaforo_5", Estado.VERDE, new Ubicacion( 30.965512534191355, -110.32051756204399), 950));
        
    }

    public static List<Semaforo> getSemaforos() {
        return SEMAFOROS;
    }


    



}
