package com.tbd.backend.repositories;

import com.tbd.backend.models.Estado_tarea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class Estado_tareaRepositoryImp implements Estado_tareaRepository{

    @Autowired
    private Sql2o sql2o;


    @Override
    public List<Estado_tarea> getAllsEstado_tareas() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from estado_tarea").executeAndFetch(Estado_tarea.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Estado_tarea createEstado_tarea(Estado_tarea eTarea) {
        try(Connection conn = sql2o.open()){
            Integer id = 0;
            id= conn.createQuery("select max(id) from estado_tarea").executeScalar(Integer.class);
            if(id == null)
            {
                id = 0;
            }
            else {
                id++;
            }
             conn.createQuery("insert into estado_tarea (id, descrip) values (:id, :descrip)")
                     .addParameter("id", id)
                    .addParameter("descrip", eTarea.getDescrip())
                    .executeUpdate();
             eTarea.setId(id);
             return eTarea;

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    @Override
    public Estado_tarea updateEstado_tarea(Estado_tarea eTarea, int id) {

        try(Connection conn = sql2o.open()){

            conn.createQuery("update estado_tarea set descrip = :descrip where id =:id")
                    .addParameter("id", id)
                    .addParameter("descrip", eTarea.getDescrip())
                    .executeUpdate();
            eTarea.setId(id);
            return eTarea;

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Estado_tarea> deleteEstado_tarea(int id) {
        try(Connection conn = sql2o.open()){

            conn.createQuery("delete from estado_tarea where id = :id")
                    .addParameter("id", id)
                    .executeUpdate();
            return this.getAllsEstado_tareas();

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
