package com.tbd.backend.repositories;

import com.tbd.backend.models.Emergencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class EmergenciaRepositoryImp implements EmergenciaRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Emergencia> getAllEmergencias(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select id, nombre, descrip, finicio, ffin, id_institucion, estado, st_x(st_astext(ubicacion)) AS longitude, st_y(st_astext(ubicacion)) AS latitude from emergencia").executeAndFetch(Emergencia.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public List<Map<String,Object>> getEmergenciasRegion() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT r.cod_regi as id_region,COUNT(d.id) as emergencias FROM emergencia AS d RIGHT JOIN division_regional AS r ON ST_WITHIN(d.ubicacion, r.geom) group by r.cod_regi")
                    .executeAndFetchTable().asList();

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Emergencia createEmergencia(Emergencia emergencia){
        try(Connection conn = sql2o.open()){
            Integer id = 0;
            id= conn.createQuery("select max(id) from emergencia").executeScalar(Integer.class);
            if(id == null)
            {
                id = 0;
            }
            else {
                id++;
            }
            String point = "POINT("+emergencia.getLongitude()+" "+emergencia.getLatitude()+")";
            conn.createQuery("insert into emergencia (id, nombre, descrip, finicio, ffin, id_institucion, estado, ubicacion) values (:id, :nombre, :descrip, :finicio, :ffin, :id_institucion, :estado, ST_GeomFromText(:point, 4326))")
                    .addParameter("estado", emergencia.getEstado())
                    .addParameter("id_institucion", emergencia.getId_institucion())
                    .addParameter("ffin", emergencia.getFfin())
                    .addParameter("finicio", emergencia.getFinicio())
                    .addParameter("descrip", emergencia.getDescrip())
                    .addParameter("nombre", emergencia.getNombre())
                    .addParameter("id", id)
                    .addParameter("point", point)
                    .executeUpdate();
            emergencia.setId(id);
            return emergencia;

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    @Override
    public Emergencia updateEmergencia(Emergencia emergencia, int id){
        try(Connection conn = sql2o.open()){
            String point = "POINT("+emergencia.getLongitude()+" "+emergencia.getLatitude()+")";
            conn.createQuery("update emergencia set estado = :estado, id_institucion = :id_institucion, ffin = :ffin, finicio = :finicio, descrip = :descrip, nombre = :nombre, ubicacion = ST_GeomFromText(:point, 4326) where id = :id")
                    .addParameter("estado", emergencia.getEstado())
                    .addParameter("id_institucion", emergencia.getId_institucion())
                    .addParameter("ffin", emergencia.getFfin())
                    .addParameter("finicio", emergencia.getFinicio())
                    .addParameter("descrip", emergencia.getDescrip())
                    .addParameter("nombre", emergencia.getNombre())
                    .addParameter("id", id)
                    .addParameter("point", point)
                    .executeUpdate();
            emergencia.setId(id);
            return emergencia;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }
    @Override
    public List<Emergencia> deleteEmergencia(int id){
        try(Connection conn = sql2o.open()){
            conn.createQuery("delete from emergencia where id = :id")
                    .addParameter("id", id)
                    .executeUpdate();
            return this.getAllEmergencias();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}