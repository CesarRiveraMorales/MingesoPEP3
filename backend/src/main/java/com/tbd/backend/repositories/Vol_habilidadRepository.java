package com.tbd.backend.repositories;

import com.tbd.backend.models.Vol_habilidad;

import java.util.List;

public interface Vol_habilidadRepository {
    public List<Vol_habilidad> getAllsVol_habilidades();
    public Vol_habilidad createVol_habilidad(Vol_habilidad vol_habilidad);
    public Vol_habilidad updateVol_habilidad(Vol_habilidad vol_habilidad, int id);
    public List<Vol_habilidad> deleteVol_habilidad(int id);
}
