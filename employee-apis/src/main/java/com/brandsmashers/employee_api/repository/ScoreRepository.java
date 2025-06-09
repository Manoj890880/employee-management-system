package com.brandsmashers.employee_api.repository;

import com.brandsmashers.employee_api.model.Score;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Long> {
  Optional<Score> findByTeamName(String teamName);
}
