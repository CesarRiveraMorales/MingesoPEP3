package com.tbd.backend.repositories;

import com.tbd.backend.models.Eme_habilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class Eme_habilidadRepositoryImp implements Eme_habilidadRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Eme_habilidad> getAllEme_habilidad(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from eme_habilidad").executeAndFetch(Eme_habilidad.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Eme_habilidad createEme_habilidad(Eme_habilidad eme_habilidad){
        try(Connection conn = sql2o.open()){
            Integer id = 0;
            id= conn.createQuery("select max(id) from eme_habilidad").executeScalar(Integer.class);
            if(id == null)
            {
                id = 0;
            }
            else {
                id++;
            }
            conn.createQuery("insert into eme_habilidad (id, id_emergencia, id_habilidad) values (:id, :id_emergencia, :id_habilidad)")
                    .addParameter("id_habilidad", eme_habilidad.getId_habilidad())
                    .addParameter("id_emergencia", eme_habilidad.getId_emergencia())
                    .addParameter("id", id)
                    .executeUpdate();
            eme_habilidad.setId(id);
            return eme_habilidad;

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    @Override
    public Eme_habilidad updateEme_habilidad(Eme_habilidad eme_habilidad, int id){
        try(Connection conn = sql2o.open()){
            conn.createQuery("update eme_habilidad set id_habilidad = :id_habilidad, id_emergencia = :id_emergencia where id = :id")
                    .addParameter("id_habilidad", eme_habilidad.getId_habilidad())
                    .addParameter("id_emergencia", eme_habilidad.getId_emergencia())
                    .addParameter("id", id)
                    .executeUpdate();
            eme_habilidad.setId(id);
            return eme_habilidad;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }
    @Override
    public List<Eme_habilidad> deleteEme_habilidad(int id){
        try(Connection conn = sql2o.open()){
            conn.createQuery("delete from eme_habilidad where id = :id")
                    .addParameter("id", id)
                    .executeUpdate();
            return this.getAllEme_habilidad();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
