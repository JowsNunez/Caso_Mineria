package com.controladores;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Congestion;
import com.services.CongestionServiceImpl;


@RestController
@RequestMapping("/api/v1")
public class CongestionController {

    @Autowired
    CongestionServiceImpl congestionService;

    @GetMapping("congestion")
    public List<Congestion> obtenerCongestiones(){
        return congestionService.obtenerTodos();
    }

    @PostMapping("congestion")
    public ResponseEntity<Congestion> guardarCongestion(@RequestBody Congestion congestion){
        Congestion nuevo = congestionService.guardar(congestion);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping("congestion/{id}")
    public ResponseEntity<Congestion> obtenerCongestionID(@PathVariable Long id){
        Congestion congestionID = congestionService.obtenPorID(id);

        return ResponseEntity.ok(congestionID);
    }

    @PutMapping("congestion/{id}")
    public ResponseEntity<Congestion> actualizar(@PathVariable Long id, @RequestBody Congestion congestion){
        Congestion congestionID = congestionService.obtenPorID(id);

        congestionID.setLocation(congestion.getLocation());
        congestionID.setType(congestion.getType());
        congestionID.setDescription(congestion.getDescription());
        congestionID.setResolve(!congestion.getResolve());
       

        Congestion congestionUpdate = congestionService.guardar(congestionID);

        return new ResponseEntity<>(congestionUpdate, HttpStatus.CREATED);
    }

    @DeleteMapping("congestion/{id}")
    public ResponseEntity<HashMap<String, Boolean>> eliminarCongestion(@PathVariable Long id){
        this.congestionService.eliminar(id);

        HashMap<String, Boolean> estadoCongestionEliminado = new HashMap<>();
        estadoCongestionEliminado.put("Eliminado", true);

        return ResponseEntity.ok(estadoCongestionEliminado);
    }
}
