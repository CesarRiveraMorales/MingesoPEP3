package com.tbd.backend.repositories;

import com.tbd.backend.models.Habilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class HabilidadRepositoryImp implements HabilidadRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Habilidad> getAllHabilidades(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from habilidad").executeAndFetch(Habilidad.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public Habilidad createHabilidad(Habilidad habilidad){

        try(Connection conn = sql2o.open()){
            Integer id = 0;
            id= conn.createQuery("select max(id) from habilidad").executeScalar(Integer.class);
            if(id == null)
            {
                id = 0;
            }
            else {
                id++;
            }
              conn.createQuery("insert into habilidad (id, descrip) values (:id, :descripcion)")
                    .addParameter("descripcion", habilidad.getDescrip())
                    .addParameter("id", id)
                    .executeUpdate();
              habilidad.setId(id);
              return habilidad;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    @Override
    public Habilidad updateHabilidad(Habilidad habilidad, int id){
        try(Connection conn = sql2o.open()){
            conn.createQuery("update habilidad set descrip = :descripcion where id = :id")
                    .addParameter("descripcion", habilidad.getDescrip())
                    .addParameter("id", id)
                    .executeUpdate();
            habilidad.setId(id);
            return habilidad;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }
    @Override
    public List<Habilidad> deleteHabilidad(int id){
        try(Connection conn = sql2o.open()){
            conn.createQuery("delete from habilidad where id = :id")
                    .addParameter("id", id)
                    .executeUpdate();
            return this.getAllHabilidades();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
