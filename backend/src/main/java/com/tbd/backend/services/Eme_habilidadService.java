package com.tbd.backend.services;

import com.tbd.backend.models.Eme_habilidad;
import com.tbd.backend.repositories.Eme_habilidadRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class Eme_habilidadService {
    private final Eme_habilidadRepository eme_habilidadRepository;

    Eme_habilidadService(Eme_habilidadRepository eme_habilidadRepository){
        this.eme_habilidadRepository = eme_habilidadRepository;
    }

    @GetMapping("/eme_habilidades")
    public List<Eme_habilidad> getAllHabilidades(){
        return eme_habilidadRepository.getAllEme_habilidad();
    }
    @PostMapping("/eme_habilidades")
    @ResponseBody
    public Eme_habilidad createEme_habilidad(@RequestBody Eme_habilidad eme_habilidad){
        Eme_habilidad result = eme_habilidadRepository.createEme_habilidad(eme_habilidad);
        return result;
    }

    @PutMapping("/eme_habilidades/{id}")
    @ResponseBody
    public Eme_habilidad updateEme_habilidad(@RequestBody Eme_habilidad eme_habilidad, @PathVariable int id) {
        Eme_habilidad result = eme_habilidadRepository.updateEme_habilidad(eme_habilidad, id);
        return result;
    }

    @DeleteMapping("/eme_habilidades/{id}")
    public List<Eme_habilidad> deleteEme_habilidad(@PathVariable int id) {
        List <Eme_habilidad> result = eme_habilidadRepository.deleteEme_habilidad(id);
        return result;
    }

}
