package com.tbd.backend.repositories;

import com.tbd.backend.models.Voluntario;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Date;

public interface VoluntarioRepository {
    public List<Voluntario> getAllsVoluntarios();
    public List<Map<String,Object>> getVoluntariosTareasIni(String estado);
    public List<Map<String,Object>> getVoluntariosEmergencias();
    public List<Map<String,Object>> getVoluntariosRegistroGraph(String fInicio, String fFin);
    public Voluntario createVoluntario(Voluntario voluntario);
    public Voluntario updateVoluntario(Voluntario voluntario, int id);
    public List<Voluntario> deleteVoluntario(int id);
}
