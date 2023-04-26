package com.itson.edu.mx.caso_mineria.semaforo;

import org.springframework.stereotype.Component;

@Component
public class Semaforo {
    private String idSemaforo;
    private Estado estado;
    private Ubicacion ubicacion;
    private Integer tiempo;


    public Semaforo(){

    }

    
    public Semaforo(String idSemaforo,Estado estado,Ubicacion ubicacion,Integer tiempo){
        this.idSemaforo=idSemaforo;
        this.estado=estado;
        this.ubicacion=ubicacion;
        this.tiempo=tiempo;
    }

    public String getIdSemaforo() {
        return idSemaforo;
    }
    public void setIdSemaforo(String idSemaforo) {
        this.idSemaforo = idSemaforo;
    }
    public Estado getEstado() {
        return estado;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    public Integer getTiempo() {
        return tiempo;
    }
    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }
    public Ubicacion getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "{"+this.idSemaforo+"}";
    }

}


enum Estado{
    NONE,VERDE,ROJO,AMARILLO
}

class Ubicacion{
    private Double latitude;
    private Double longitude;

    public Ubicacion(){

    }
    public Ubicacion(Double latitude,Double longitde){
        this.latitude=latitude;
        this.longitude=longitde;
    }

    

        public Double getLatitude() {
        return latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    

}