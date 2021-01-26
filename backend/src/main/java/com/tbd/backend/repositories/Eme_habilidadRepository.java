package com.tbd.backend.repositories;
import com.tbd.backend.models.Eme_habilidad;

import java.util.List;

public interface Eme_habilidadRepository {
    public List<Eme_habilidad> getAllEme_habilidad();
    public Eme_habilidad createEme_habilidad (Eme_habilidad eme_habilidad);
    public Eme_habilidad updateEme_habilidad (Eme_habilidad eme_habilidad, int id);
    public List<Eme_habilidad> deleteEme_habilidad (int id);
}
