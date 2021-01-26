package com.tbd.backend.services;

import com.tbd.backend.models.Emergencia;
import com.tbd.backend.repositories.EmergenciaRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class EmergenciaService {
    private final EmergenciaRepository emergenciaRepository;

    EmergenciaService(EmergenciaRepository emergenciaRepository){
        this.emergenciaRepository = emergenciaRepository;
    }

    @GetMapping("/emergencias")
    public List<Emergencia> getAllEmergencias(){
        return emergenciaRepository.getAllEmergencias();
    }

    @GetMapping("/emergencias/region")
    public List<Map<String,Object>> getEmergenciasRegion(){
        return emergenciaRepository.getEmergenciasRegion();
    }
    @PostMapping("/emergencias")
    @ResponseBody
    public Emergencia createEmergencia(@RequestBody Emergencia emergencia){
        Emergencia result = emergenciaRepository.createEmergencia(emergencia);
        return result;
    }

    @PutMapping("/emergencias/{id}")
    @ResponseBody
    public Emergencia updateEmergencia(@RequestBody Emergencia emergencia, @PathVariable int id) {
        Emergencia result = emergenciaRepository.updateEmergencia(emergencia, id);
        return result;
    }

    @DeleteMapping("/emergencias/{id}")
    public List<Emergencia> deleteEmergencia(@PathVariable int id) {
        List <Emergencia> result = emergenciaRepository.deleteEmergencia(id);
        return result;
    }

}