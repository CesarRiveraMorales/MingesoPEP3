package com.tbd.backend.services;

import com.tbd.backend.models.Ranking;
import com.tbd.backend.repositories.RankingRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class RankingService {
    private final RankingRepository rankingRepository;

    RankingService(RankingRepository rankingRepository){
        this.rankingRepository = rankingRepository;
    }

    @GetMapping("/rankings")
    public List<Ranking> getAllRankings(){
        return rankingRepository.getAllRankings();
    }
    @PostMapping("/rankings")
    @ResponseBody
    public Ranking createRanking(@RequestBody Ranking ranking){
        Ranking result = rankingRepository.createRanking(ranking);
        return result;
    }

    @PutMapping("/rankings/{id}")
    @ResponseBody
    public Ranking updateRanking(@RequestBody Ranking ranking, @PathVariable int id) {
        Ranking result = rankingRepository.updateRanking(ranking, id);
        return result;
    }

    @DeleteMapping("/rankings/{id}")
    public List<Ranking> deleteRanking(@PathVariable int id) {
        List <Ranking> result = rankingRepository.deleteRanking(id);
        return result;
    }

}
