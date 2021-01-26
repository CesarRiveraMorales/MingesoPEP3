package com.tbd.backend.repositories;

import com.tbd.backend.models.Tarea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TareaRepositoryImp implements TareaRepository {
    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Tarea> getAllsTareas(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select id, nombre, descrip, cant_vol_requeridos, cant_vol_inscritos, id_emergencia, finicio, ffin, id_estado, st_x(st_astext(ubicacion)) AS longitude, st_y(st_astext(ubicacion)) AS latitude from tarea").executeAndFetch(Tarea.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Map<String,Object>> getTareaRegion() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT r.cod_regi as id_region,COUNT(d.id) as tareas FROM tarea AS d RIGHT JOIN division_regional AS r ON ST_WITHIN(d.ubicacion, r.geom) group by r.cod_regi")
                    .executeAndFetchTable().asList();

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Tarea createTarea(Tarea tarea){
        try(Connection conn = sql2o.open()){
            Integer id = 0;
            id= conn.createQuery("select max(id) from tarea").executeScalar(Integer.class);
            if(id == null)
            {
                id = 0;
            }
            else {
                id++;
            }
            String point = "POINT("+tarea.getLongitude()+" "+tarea.getLatitude()+")";
            conn.createQuery("insert into tarea (id, nombre, descrip, cant_vol_requeridos, cant_vol_inscritos, id_emergencia, finicio, ffin, id_estado, ubicacion) " +
                    "values (:id, :nombre, :descripcion, :cant_vol_requeridos, :cant_vol_inscritos, :id_emergencia, :finicio, :ffin, :id_estado, ST_GeomFromText(:point, 4326))")
                    .addParameter("id",id)
                    .addParameter("nombre",tarea.getNombre())
                    .addParameter("descripcion",tarea.getDescrip())
                    .addParameter("cant_vol_requeridos",tarea.getCant_vol_requeridos())
                    .addParameter("cant_vol_inscritos",tarea.getCant_vol_inscritos())
                    .addParameter("id_emergencia",tarea.getId_emergencia())
                    .addParameter("finicio", tarea.getFinicio())
                    .addParameter("ffin", tarea.getFfin())
                    .addParameter("id_estado", tarea.getId_estado())
                    .addParameter("point", point)
                    .executeUpdate();
            tarea.setId(id);
            return tarea;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    @Override
    public Tarea updateTarea(Tarea tarea, int id){
        try(Connection conn = sql2o.open()){
            String point = "POINT("+tarea.getLongitude()+" "+tarea.getLatitude()+")";
            conn.createQuery("update tarea set  nombre = :nombre, descrip = :descripcion," +
                            " cant_vol_requeridos = :cant_vol_requeridos, cant_vol_inscritos = :cant_vol_inscritos, id_emergencia = :id_emergencia" +
                            ", finicio = :finicio, ffin = :ffin, id_estado = :id_estado, ubicacion = ST_GeomFromText(:point, 4326) where id = :id")
                    .addParameter("id",id)
                    .addParameter("nombre",tarea.getNombre())
                    .addParameter("descripcion",tarea.getDescrip())
                    .addParameter("cant_vol_requeridos",tarea.getCant_vol_requeridos())
                    .addParameter("cant_vol_inscritos",tarea.getCant_vol_inscritos())
                    .addParameter("id_emergencia",tarea.getId_emergencia())
                    .addParameter("finicio", tarea.getFinicio())
                    .addParameter("ffin", tarea.getFfin())
                    .addParameter("id_estado", tarea.getId_estado())
                    .addParameter("point", point)
                    .executeUpdate();
            tarea.setId(id);
            return tarea;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Tarea> deleteTarea (int id){
        try(Connection conn = sql2o.open()) {
            conn.createQuery("delete from tarea where id = :id")
                    .addParameter("id", id)
                    .executeUpdate();
            return this.getAllsTareas();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }


    }

    @Override
    public Boolean calcularPuntaje(int id) {
        try(Connection conn = sql2o.open()) {
            conn.createQuery("CALL public.calculo_puntaje(:id)")
                    .addParameter("id", id)
                    .executeUpdate();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }


}
