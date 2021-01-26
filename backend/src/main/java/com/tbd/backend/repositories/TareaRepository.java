package com.tbd.backend.repositories;

import com.tbd.backend.models.Tarea;

import java.util.List;
import java.util.Map;

public interface TareaRepository {
    public List<Tarea> getAllsTareas();
    public List<Map<String,Object>> getTareaRegion();
    public Tarea createTarea(Tarea tarea);
    public Tarea updateTarea(Tarea tarea, int id);
    public List<Tarea> deleteTarea(int id);
    public Boolean calcularPuntaje (int id);


}
