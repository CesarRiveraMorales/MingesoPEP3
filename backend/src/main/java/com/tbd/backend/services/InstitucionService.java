package com.tbd.backend.services;

import com.tbd.backend.models.Institucion;
import com.tbd.backend.repositories.InstitucionRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class InstitucionService {
    private final InstitucionRepository institucionRepository;

    InstitucionService(InstitucionRepository institucionRepository){
        this.institucionRepository = institucionRepository;
    }

    @GetMapping("/instituciones")
    public List<Institucion> getAllInstituciones(){
        return institucionRepository.getAllInstituciones();
    }
    @PostMapping("/instituciones")
    @ResponseBody
    public Institucion createInstitucion(@RequestBody Institucion institucion){
        Institucion result = institucionRepository.createInstitucion(institucion);
        return result;
    }

    @PutMapping("/instituciones/{id}")
    @ResponseBody
    public Institucion updateInstitucion(@RequestBody Institucion institucion, @PathVariable int id) {
        Institucion result = institucionRepository.updateInstitucion(institucion, id);
        return result;
    }

    @DeleteMapping("/instituciones/{id}")
    public List<Institucion> deleteInstitucion(@PathVariable int id) {
        List <Institucion> result = institucionRepository.deleteInstitucion(id);
        return result;
    }

}