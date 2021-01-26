package com.tbd.backend.services;

import com.tbd.backend.models.Voluntario;
import com.tbd.backend.repositories.VoluntarioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Date;

@CrossOrigin
@RestController
public class VoluntarioService {
    private final VoluntarioRepository voluntarioRepository;

    VoluntarioService (VoluntarioRepository voluntarioRepository){
        this.voluntarioRepository = voluntarioRepository;
    }
    @GetMapping("/voluntarios")
    public List<Voluntario> getAllsVoluntarios(){
        return this.voluntarioRepository.getAllsVoluntarios();
    }

    @GetMapping("/voluntarios/tareas/{estado}")
    public List<Map<String,Object>> getVoluntariosTareasIni(@PathVariable String estado){
        return this.voluntarioRepository.getVoluntariosTareasIni(estado);
    }


    @GetMapping("/voluntarios/emergencias/activas/region")
    public List<Map<String,Object>> getVoluntariosEmergencias(){
        return this.voluntarioRepository.getVoluntariosEmergencias();
    }

    @GetMapping("/voluntarios/dias/{finicio}to{ffin}")
    public List<Map<String,Object>> getVoluntariosRegistroGraph(@PathVariable("finicio") String finicio, @PathVariable("ffin") String ffin){
        return this.voluntarioRepository.getVoluntariosRegistroGraph(finicio, ffin);
    }

    @PostMapping("/voluntarios")
    @ResponseBody
    public Voluntario createVoluntario(@RequestBody Voluntario voluntario) {
        return this.voluntarioRepository.createVoluntario(voluntario);
    }
    @PutMapping("/voluntarios/{id}")
    @ResponseBody
    public Voluntario updateVoluntario(@RequestBody Voluntario voluntario, @PathVariable int id) {
        return this.voluntarioRepository.updateVoluntario(voluntario, id);
    }

    @DeleteMapping("/voluntarios/{id}")
    public List<Voluntario> deleteVoluntario(@PathVariable int id) {
        return this.voluntarioRepository.deleteVoluntario(id);
    }
}
