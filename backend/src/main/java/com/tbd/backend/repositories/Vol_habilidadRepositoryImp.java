package com.tbd.backend.repositories;


import com.tbd.backend.models.Vol_habilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class Vol_habilidadRepositoryImp implements Vol_habilidadRepository{
    @Autowired
    Sql2o sql2o;


    @Override
    public List<Vol_habilidad> getAllsVol_habilidades() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from vol_habilidad").executeAndFetch(Vol_habilidad.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Vol_habilidad createVol_habilidad(Vol_habilidad vol_habilidad) {
        try(Connection conn = sql2o.open()){
            Integer idMayor = conn.createQuery("select max(id) from vol_habilidad").executeScalar(Integer.class);
            if(idMayor == null)
            {
                idMayor = 0;
            }
            else {
                idMayor++;
            }
            conn.createQuery("insert into vol_habilidad (id, id_voluntario, id_habilidad) values (:id, :id_voluntario, :id_habilidad)")
                    .addParameter("id",idMayor)
                    .addParameter("id_voluntario", vol_habilidad.getId_voluntario())
                    .addParameter("id_habilidad", vol_habilidad.getId_habilidad())
                    .executeUpdate();
            vol_habilidad.setId(idMayor);
            return vol_habilidad;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Vol_habilidad updateVol_habilidad(Vol_habilidad vol_habilidad, int id) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("update vol_habilidad  set id_voluntario = :id_voluntario, id_habilidad = :id_habilidad where id = :id")
                    .addParameter("id",id)
                    .addParameter("id_voluntario", vol_habilidad.getId_voluntario())
                    .addParameter("id_habilidad", vol_habilidad.getId_habilidad())
                    .executeUpdate();
            vol_habilidad.setId(id);
            return vol_habilidad;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Vol_habilidad> deleteVol_habilidad(int id) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("delete from vol_habilidad where id = :id")
                    .addParameter("id", id)
                    .executeUpdate();
            return this.getAllsVol_habilidades();
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
