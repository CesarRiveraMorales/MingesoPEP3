package com.tbd.backend.repositories;

import com.tbd.backend.models.Tarea_habilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class Tarea_habilidadRepositoryImp implements Tarea_habilidadRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Tarea_habilidad> getAllTarea_habilidad(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from tarea_habilidad").executeAndFetch(Tarea_habilidad.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Tarea_habilidad createTarea_habilidad(Tarea_habilidad tarea_habilidad){
        try(Connection conn = sql2o.open()){
            Integer id = 0;
            id= conn.createQuery("select max(id) from tarea_habilidad").executeScalar(Integer.class);
            if(id == null)
            {
                id = 0;
            }
            else {
                id++;
            }
            conn.createQuery("insert into tarea_habilidad (id, id_emehab, id_tarea) values (:id, :id_emehab, :id_tarea)")
                    .addParameter("id_tarea", tarea_habilidad.getId_tarea())
                    .addParameter("id_emehab", tarea_habilidad.getId_emehab())
                    .addParameter("id", id)
                    .executeUpdate();
            tarea_habilidad.setId(id);
            return tarea_habilidad;

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    @Override
    public Tarea_habilidad updateTarea_habilidad(Tarea_habilidad tarea_habilidad, int id){
        try(Connection conn = sql2o.open()){
            conn.createQuery("update tarea_habilidad set id_tarea = :id_tarea, id_emehab = :id_emehab where id = :id")
                    .addParameter("id_tarea", tarea_habilidad.getId_tarea())
                    .addParameter("id_emehab", tarea_habilidad.getId_emehab())
                    .addParameter("id", id)
                    .executeUpdate();
            tarea_habilidad.setId(id);
            return tarea_habilidad;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }
    @Override
    public List<Tarea_habilidad> deleteTarea_habilidad(int id){
        try(Connection conn = sql2o.open()){
            conn.createQuery("delete from tarea_habilidad where id = :id")
                    .addParameter("id", id)
                    .executeUpdate();
            return this.getAllTarea_habilidad();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}