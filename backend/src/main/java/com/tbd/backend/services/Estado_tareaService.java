package com.tbd.backend.services;

import com.tbd.backend.models.Estado_tarea;
import com.tbd.backend.repositories.Estado_tareaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class Estado_tareaService {
    private final Estado_tareaRepository estado_tarea;

    Estado_tareaService(Estado_tareaRepository estado_tarea){
        this.estado_tarea=estado_tarea;
    }

    @GetMapping("/estado_tareas")
    public List<Estado_tarea> getAllsEstado_tareas(){
        return estado_tarea.getAllsEstado_tareas();
    }

    @PostMapping("/estado_tareas")
    @ResponseBody
    public Estado_tarea createEstado_tarea(@RequestBody Estado_tarea eTarea){
        return estado_tarea.createEstado_tarea(eTarea);
    }

    @PutMapping("/estado_tareas/{id}")
    @ResponseBody
    public Estado_tarea updateEstado_tarea(@RequestBody Estado_tarea eTarea, @PathVariable int id){
        return estado_tarea.updateEstado_tarea(eTarea, id);
    }
    @DeleteMapping("/estado_tareas/{id}")
    public List<Estado_tarea> deleteEstado_tarea(@PathVariable int id){
        return estado_tarea.deleteEstado_tarea(id);
    }
}
