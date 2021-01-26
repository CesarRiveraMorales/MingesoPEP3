package com.tbd.backend.services;

import com.tbd.backend.models.Tarea_habilidad;
import com.tbd.backend.repositories.Tarea_habilidadRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class Tarea_habilidadService {
    private final Tarea_habilidadRepository tarea_habilidadRepository;

    Tarea_habilidadService(Tarea_habilidadRepository tarea_habilidadRepository){
        this.tarea_habilidadRepository = tarea_habilidadRepository;
    }

    @GetMapping("/tarea_habilidades")
    public List<Tarea_habilidad> getAllTarea_habilidad(){
        return tarea_habilidadRepository.getAllTarea_habilidad();
    }
    @PostMapping("/tarea_habilidades")
    @ResponseBody
    public Tarea_habilidad createTarea_habilidad(@RequestBody Tarea_habilidad tarea_habilidad){
        Tarea_habilidad result = tarea_habilidadRepository.createTarea_habilidad(tarea_habilidad);
        return result;
    }

    @PutMapping("/tarea_habilidades/{id}")
    @ResponseBody
    public Tarea_habilidad updateTarea_habilidad(@RequestBody Tarea_habilidad tarea_habilidad, @PathVariable int id) {
        Tarea_habilidad result = tarea_habilidadRepository.updateTarea_habilidad(tarea_habilidad, id);
        return result;
    }

    @DeleteMapping("/tarea_habilidades/{id}")
    public List<Tarea_habilidad> deleteTarea_habilidad(@PathVariable int id) {
        List <Tarea_habilidad> result = tarea_habilidadRepository.deleteTarea_habilidad(id);
        return result;
    }

}
