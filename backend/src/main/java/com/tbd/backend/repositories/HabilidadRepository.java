package com.tbd.backend.repositories;
import com.tbd.backend.models.Habilidad;

import java.util.List;

public interface HabilidadRepository {
    public List<Habilidad> getAllHabilidades();
    public Habilidad createHabilidad (Habilidad habilidad);
    public Habilidad updateHabilidad (Habilidad habilidad, int id);
    public List<Habilidad> deleteHabilidad (int id);
}
