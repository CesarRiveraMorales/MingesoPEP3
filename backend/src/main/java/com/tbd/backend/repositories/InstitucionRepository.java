package com.tbd.backend.repositories;
import com.tbd.backend.models.Institucion;

import java.util.List;

public interface InstitucionRepository {
    public List<Institucion> getAllInstituciones();
    public Institucion createInstitucion (Institucion institucion);
    public Institucion updateInstitucion (Institucion institucion, int id);
    public List<Institucion> deleteInstitucion (int id);
}
