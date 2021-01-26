package com.tbd.backend.repositories;

import com.tbd.backend.models.Ranking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class RankingRepositoryImp implements RankingRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Ranking> getAllRankings(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from ranking").executeAndFetch(Ranking.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Ranking createRanking(Ranking ranking){
        try(Connection conn = sql2o.open()){
            Integer id = 0;
            id= conn.createQuery("select max(id) from ranking").executeScalar(Integer.class);
            if(id == null)
            {
                id = 0;
            }
            else {
                id++;
            }
            conn.createQuery("insert into ranking (id, id_voluntario, id_tarea, puntaje, flg_invitado, flg_participa) values (:id, :id_voluntario, :id_tarea, :puntaje, :flg_invitado, :flg_participa)")
                    .addParameter("flg_participa", ranking.getFlg_participa())
                    .addParameter("flg_invitado", ranking.getFlg_invitado())
                    .addParameter("puntaje", ranking.getPuntaje())
                    .addParameter("id_tarea", ranking.getId_tarea())
                    .addParameter("id_voluntario", ranking.getId_voluntario())
                    .addParameter("id", id)
                    .executeUpdate();
            ranking.setId(id);
            return ranking;

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    @Override
    public Ranking updateRanking(Ranking ranking, int id){
        try(Connection conn = sql2o.open()){
            conn.createQuery("update ranking set flg_participa = :flg_participa, flg_invitado = :flg_invitado, puntaje = :puntaje, id_tarea = :id_tarea, id_voluntario = :id_voluntario where id = :id")
                    .addParameter("flg_participa", ranking.getFlg_participa())
                    .addParameter("flg_invitado", ranking.getFlg_invitado())
                    .addParameter("puntaje", ranking.getPuntaje())
                    .addParameter("id_tarea", ranking.getId_tarea())
                    .addParameter("id_voluntario", ranking.getId_voluntario())
                    .addParameter("id", id)
                    .executeUpdate();
            ranking.setId(id);
            return ranking;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }
    @Override
    public List<Ranking> deleteRanking(int id){
        try(Connection conn = sql2o.open()){
            conn.createQuery("delete from ranking where id = :id")
                    .addParameter("id", id)
                    .executeUpdate();
            return this.getAllRankings();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}