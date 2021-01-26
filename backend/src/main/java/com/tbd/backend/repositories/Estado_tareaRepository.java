package com.tbd.backend.repositories;


import com.tbd.backend.models.Estado_tarea;

import java.util.List;

public interface Estado_tareaRepository {
    public List<Estado_tarea> getAllsEstado_tareas();
    public Estado_tarea createEstado_tarea(Estado_tarea eTarea);
    public Estado_tarea updateEstado_tarea(Estado_tarea eTarea, int id);
    public List<Estado_tarea> deleteEstado_tarea(int id);
}
