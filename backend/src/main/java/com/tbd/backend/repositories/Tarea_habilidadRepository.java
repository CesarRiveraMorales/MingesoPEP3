package com.tbd.backend.repositories;
import com.tbd.backend.models.Tarea_habilidad;

import java.util.List;

public interface Tarea_habilidadRepository {
    public List<Tarea_habilidad> getAllTarea_habilidad();
    public Tarea_habilidad createTarea_habilidad (Tarea_habilidad tarea_habilidad);
    public Tarea_habilidad updateTarea_habilidad (Tarea_habilidad tarea_habilidad, int id);
    public List<Tarea_habilidad> deleteTarea_habilidad (int id);
}
