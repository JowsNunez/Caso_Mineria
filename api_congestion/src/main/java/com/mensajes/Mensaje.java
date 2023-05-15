package com.mensajes;

public class Mensaje {

    private String accion;

    private Object contenido;

    public Mensaje(){}

    public String getAccion() {
        return accion;
    }
    public Object getContenido() {
        return contenido;
    }
    public void setAccion(String accion) {
        this.accion = accion;
    }
    public void setContenido(Object contenido) {
        this.contenido = contenido;
    }


    
}
