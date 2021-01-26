package com.tbd.backend.services;

import com.tbd.backend.models.Habilidad;
import com.tbd.backend.repositories.HabilidadRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class HabilidadService {
    private final HabilidadRepository habilidadRepository;

    HabilidadService(HabilidadRepository habilidadRepository){
        this.habilidadRepository = habilidadRepository;
    }

    @GetMapping("/habilidades")
    public List<Habilidad> getAllHabilidades(){
        return habilidadRepository.getAllHabilidades();
    }
    @PostMapping("/habilidades")
    @ResponseBody
    public Habilidad createHabilidad(@RequestBody Habilidad habilidad){
        Habilidad result = habilidadRepository.createHabilidad(habilidad);
        return result;
    }

    @PutMapping("/habilidades/{id}")
    @ResponseBody
    public Habilidad updateHabilidad(@RequestBody Habilidad habilidad, @PathVariable int id) {
        Habilidad result = habilidadRepository.updateHabilidad(habilidad, id);
        return result;
    }

    @DeleteMapping("/habilidades/{id}")
    public List<Habilidad> deleteHabilidad(@PathVariable int id) {
        List <Habilidad> result = habilidadRepository.deleteHabilidad(id);
        return result;
    }


}
