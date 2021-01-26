package com.tbd.backend.repositories;

import com.tbd.backend.models.Institucion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class InstitucionRepositoryImp implements InstitucionRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Institucion> getAllInstituciones(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from institucion").executeAndFetch(Institucion.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Institucion createInstitucion(Institucion institucion){
        try(Connection conn = sql2o.open()){
            Integer id = 0;
            id= conn.createQuery("select max(id) from institucion").executeScalar(Integer.class);
            if(id == null)
            {
                id = 0;
            }
            else {
                id++;
            }
            conn.createQuery("insert into institucion (id, nombre, descrip) values (:id, :nombre, :descrip)")
                    .addParameter("descrip", institucion.getDescrip())
                    .addParameter("nombre", institucion.getNombre())
                    .addParameter("id", id)
                    .executeUpdate();
            institucion.setId(id);
            return institucion;

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    @Override
    public Institucion updateInstitucion(Institucion institucion, int id){
        try(Connection conn = sql2o.open()){
            conn.createQuery("update institucion set descrip = :descrip, nombre = :nombre where id = :id")
                    .addParameter("descrip", institucion.getDescrip())
                    .addParameter("nombre", institucion.getNombre())
                    .addParameter("id", id)
                    .executeUpdate();
            institucion.setId(id);
            return institucion;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }
    @Override
    public List<Institucion> deleteInstitucion(int id){
        try(Connection conn = sql2o.open()){
            conn.createQuery("delete from institucion where id = :id")
                    .addParameter("id", id)
                    .executeUpdate();
            return this.getAllInstituciones();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
