package com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entities.Congestion;
import com.mensajes.Mensaje;
import com.repositories.CongestionRepository;

@Service
public class CongestionServiceImpl implements ICongestionServices {

    @Autowired
    CongestionRepository congestionRepository;

    @Autowired
    MensajeServiceImpl mensajeService;

    @Override
    public List<Congestion> obtenerTodos() {
        return congestionRepository.findAll();
    } 

    @Override
    public Congestion guardar(Congestion congestion) {

        Congestion newCongestion = congestionRepository.save(congestion);
        System.out.println(newCongestion.getResolve());
        if (newCongestion != null) {
            Mensaje mensaje = new Mensaje();

            mensaje.setAccion("env");
            mensaje.setContenido(this.obtenPorResolve());

            this.mensajeService.send(mensaje);

        }
        return newCongestion;
    }

    @Override
    public Congestion obtenPorID(Long id) {
        return congestionRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        congestionRepository.deleteById(id);
    }

    @Override
    public Iterable<Congestion> obtenPorResolve() {
        return congestionRepository.getCongestionByResolve();
    }

}
