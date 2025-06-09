package com.brandsmashers.employee_api.repository;

import com.brandsmashers.employee_api.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    Optional<Score> findByTeamName(String teamName);
}
