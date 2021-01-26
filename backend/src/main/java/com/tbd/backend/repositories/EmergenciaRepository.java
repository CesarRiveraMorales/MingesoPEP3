package com.tbd.backend.repositories;
import com.tbd.backend.models.Emergencia;

import java.util.List;
import java.util.Map;

public interface EmergenciaRepository {
    public List<Emergencia> getAllEmergencias();
    public List<Map<String,Object>> getEmergenciasRegion();
    public Emergencia createEmergencia (Emergencia emergencia);
    public Emergencia updateEmergencia (Emergencia emergencia, int id);
    public List<Emergencia> deleteEmergencia (int id);
}
