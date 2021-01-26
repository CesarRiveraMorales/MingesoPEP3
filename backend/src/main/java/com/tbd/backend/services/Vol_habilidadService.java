package com.tbd.backend.services;

import com.tbd.backend.models.Vol_habilidad;
import com.tbd.backend.repositories.Vol_habilidadRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class Vol_habilidadService {
    private final Vol_habilidadRepository vol_habilidadRepository;
    Vol_habilidadService (Vol_habilidadRepository vol_habilidadRepository){
        this.vol_habilidadRepository = vol_habilidadRepository;
    }

    @GetMapping("/vol_habilidades")
    public List<Vol_habilidad> getAllsVol_habilidades(){
        return this.vol_habilidadRepository.getAllsVol_habilidades();
    }

    @PostMapping("/vol_habilidades")
    @ResponseBody
    public Vol_habilidad createVol_habilidad(@RequestBody Vol_habilidad vol_habilidad){
        return this.vol_habilidadRepository.createVol_habilidad(vol_habilidad);
    }

    @PutMapping("/vol_habilidades/{id}")
    @ResponseBody
    public Vol_habilidad updateVol_habilidad(@RequestBody Vol_habilidad vol_habilidad, @PathVariable int id){
        return this.vol_habilidadRepository.updateVol_habilidad(vol_habilidad,id);
    }

    @DeleteMapping("/vol_habilidades/{id}")
    public List<Vol_habilidad> deleteVol_habilidad(@PathVariable int id){
        return this.vol_habilidadRepository.deleteVol_habilidad(id);
    }
}
