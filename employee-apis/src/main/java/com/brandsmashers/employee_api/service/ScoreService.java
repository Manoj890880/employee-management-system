package com.brandsmashers.employee_api.service;

import com.brandsmashers.employee_api.model.Score;
import com.brandsmashers.employee_api.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {
  @Autowired private ScoreRepository repo;

  public Score updateScore(String teamName, int newScore) {
    Score score =
        repo.findByTeamName(teamName).orElseThrow(() -> new RuntimeException("Team not found"));
    score.setScore(newScore);
    return repo.save(score);
  }

  public Score getScoreByTeamName(String teamName) {
    return repo.findByTeamName(teamName).orElseThrow(() -> new RuntimeException("Team not found"));
  }
}
