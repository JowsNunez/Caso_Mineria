package com.services;

import java.util.List;

import com.entities.Congestion;

public interface ICongestionServices {
    
    public List<Congestion> obtenerTodos();

    public Congestion guardar(Congestion congestion);

    public Congestion obtenPorID(Long id);
    public Iterable<Congestion> obtenPorResolve();

    public void eliminar(Long id);
}
