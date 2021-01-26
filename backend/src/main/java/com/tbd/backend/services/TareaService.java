package com.tbd.backend.services;


import com.tbd.backend.models.Tarea;
import com.tbd.backend.repositories.TareaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class TareaService {

    private final TareaRepository tareaRepository;

    TareaService(TareaRepository tareaRepository){
        this.tareaRepository = tareaRepository;
    }

    @GetMapping("/tareas")
    public List<Tarea> getAllsTareas(){
        return tareaRepository.getAllsTareas();
    }

    @GetMapping("/tareas/region")
    public List<Map<String,Object>> getTareaRegion(){
        return tareaRepository.getTareaRegion();
    }

    @PostMapping("/tareas")
    @ResponseBody
    public Tarea createTarea(@RequestBody Tarea tarea){
        Tarea result = tareaRepository.createTarea(tarea);
        return result;
    }

    @PutMapping("/tareas/{id}")
    @ResponseBody
    public Tarea updateTarea(@RequestBody Tarea tarea, @PathVariable int id){
        return tareaRepository.updateTarea(tarea, id);
    }

    @DeleteMapping("/tareas/{id}")
    public List<Tarea> deleteTarea (@PathVariable int id){
        return tareaRepository.deleteTarea(id);
    }

    @PutMapping("/tareas/calcularPuntajes/{id}")
    public Boolean calcularPuntaje (@PathVariable int id){
        return tareaRepository.calcularPuntaje(id);
    }

}
