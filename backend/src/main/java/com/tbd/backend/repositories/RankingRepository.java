package com.tbd.backend.repositories;
import com.tbd.backend.models.Ranking;

import java.util.List;

public interface RankingRepository {
    public List<Ranking> getAllRankings();
    public Ranking createRanking (Ranking ranking);
    public Ranking updateRanking (Ranking ranking, int id);
    public List<Ranking> deleteRanking (int id);
}
