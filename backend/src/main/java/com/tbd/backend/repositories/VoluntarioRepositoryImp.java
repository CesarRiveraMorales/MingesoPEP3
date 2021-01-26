package com.tbd.backend.repositories;

import com.tbd.backend.models.Voluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Repository
public class VoluntarioRepositoryImp implements VoluntarioRepository{
    @Autowired
    Sql2o sql2o;

    @Override
    public List<Voluntario> getAllsVoluntarios() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select id, nombre, fnacimiento, st_x(st_astext(ubicacion)) AS longitude, st_y(st_astext(ubicacion)) AS latitude, fregistro from voluntario")
                    .executeAndFetch(Voluntario.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Map<String,Object>> getVoluntariosTareasIni(String estado) {
        try(Connection conn = sql2o.open()){
            List<Map<String,Object>> tareasVoluntarios = conn.createQuery("SELECT idtarea.id,idtarea.nombre, COUNT(r.id) " +
                    "FROM (SELECT t.id, t.nombre FROM tarea t, estado_tarea e  WHERE (t.id_estado = e.id) AND (e.descrip = :estado)) AS idtarea " +
                    "LEFT JOIN ranking AS r ON idtarea.id = r.id_tarea  group by idtarea.id,idtarea.nombre")
                    .addParameter("estado",estado)
                    .executeAndFetchTable().asList();
            return tareasVoluntarios;


        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public List<Map<String, Object>> getVoluntariosEmergencias() {
        try(Connection conn = sql2o.open()){
            List<Map<String,Object>> emergenciasVoluntarios = conn.createQuery("SElECT r.cod_regi AS region,COUNT(v.ubicacion) AS voluntarios FROM (SELECT v.ubicacion FROM (SELECT r.id_voluntario AS id_voluntario " +
                    "FROM (SELECT t.id AS id_tarea FROM tarea AS t INNER JOIN emergencia AS e ON e.estado='Iniciada' AND e.id=t.id_emergencia) AS t " +
                            "INNER JOIN ranking AS r ON t.id_tarea=r.id_tarea GROUP BY r.id_voluntario) AS a INNER JOIN voluntario AS v ON a.id_voluntario=v.id) AS v " +
                            "RIGHT JOIN division_regional AS r ON ST_WITHIN(v.ubicacion, r.geom)" +
                            "GROUP BY r.cod_regi")
                    .executeAndFetchTable().asList();
            return emergenciasVoluntarios;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getVoluntariosRegistroGraph(String fInicio, String fFin) {
        try (Connection conn = sql2o.open()) {
            try{
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date finicio =  sdf.parse(fInicio);
                Date ffin =  sdf.parse(fFin);
                if(finicio.compareTo(ffin) <= 0) {
                    List<Map<String, Object>> voluntariosRegistroGraph = new ArrayList<Map<String,Object>>();
                    Map<String, Object> diaAux;
                    while (finicio.compareTo(ffin) <= 0) {
                        diaAux = new HashMap<String, Object>();
                        diaAux.put("fecha", new Date(finicio.getYear(), finicio.getMonth(),finicio.getDate()));
                        diaAux.put("voluntarios", conn.createQuery("SELECT COUNT(*) FROM voluntario WHERE fregistro = :fecha").addParameter("fecha", finicio).executeScalar(Integer.class));
                        voluntariosRegistroGraph.add(diaAux);
                        finicio.setDate(finicio.getDate()+1);
                    }
                    return voluntariosRegistroGraph;
                }else{
                    List<Map<String, Object>> voluntariosRegistroGraph = new ArrayList<Map<String,Object>>();
                    Map<String, Object> diaAux;
                    Date aux = ffin;
                    ffin = finicio;
                    finicio = aux;
                    while (finicio.compareTo(ffin) <= 0) {
                        diaAux = new HashMap<String, Object>();
                        diaAux.put("fecha", new Date(finicio.getYear(), finicio.getMonth(),finicio.getDate()));
                        diaAux.put("voluntarios", conn.createQuery("SELECT COUNT(*) FROM voluntario WHERE fregistro = :fecha").addParameter("fecha", finicio).executeScalar(Integer.class));
                        voluntariosRegistroGraph.add(diaAux);
                        finicio.setDate(finicio.getDate()+1);
                    }
                    return voluntariosRegistroGraph;
                }
            }catch (ParseException e) {
                e.printStackTrace();

                return null;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Voluntario createVoluntario(Voluntario voluntario) {
        try(Connection conn = sql2o.open()){
            Integer idMayor = 0;
            idMayor= conn.createQuery("select max(id) from voluntario").executeScalar(Integer.class);
            if(idMayor == null)
            {
                idMayor = 0;
            }
            else {
                idMayor++;
            }
            String point = "POINT("+voluntario.getLongitude()+" "+voluntario.getLatitude()+")";
            conn.createQuery("insert into voluntario (id, nombre, fnacimiento, ubicacion, fregistro) values (:id, :nombre, :fnacimiento, ST_GeomFromText(:point, 4326), :fregistro)")
                    .addParameter("id",idMayor)
                    .addParameter("nombre", voluntario.getNombre())
                    .addParameter("fnacimiento", voluntario.getFnacimiento())
                    .addParameter("point", point)
                    .addParameter("fregistro", voluntario.getFregistro())
                    .executeUpdate();
            voluntario.setId(idMayor);
            return voluntario;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;

        }

    }

    @Override
    public Voluntario updateVoluntario(Voluntario voluntario, int id) {
        try(Connection conn = sql2o.open()){
            String point = "POINT("+voluntario.getLongitude()+" "+voluntario.getLatitude()+")";
            conn.createQuery("update voluntario set nombre = :nombre, fnacimiento = :fnacimiento, ubicacion = ST_GeomFromText(:point, 4326), fregistro = :fregistro where id = :id")
                    .addParameter("id",id)
                    .addParameter("nombre", voluntario.getNombre())
                    .addParameter("fnacimiento", voluntario.getFnacimiento())
                    .addParameter("point", point)
                    .addParameter("fregistro", voluntario.getFregistro())
                    .executeUpdate();
            voluntario.setId(id);
            return voluntario;
            }catch (Exception e){
                System.out.println(e.getMessage());
                return null;

            }
    }

    @Override
    public List<Voluntario> deleteVoluntario(int id) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("delete from voluntario where id =:id")
                    .addParameter("id",id)
                    .executeUpdate();
            return this.getAllsVoluntarios();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;

        }
    }
}
