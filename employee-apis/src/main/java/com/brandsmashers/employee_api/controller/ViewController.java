package com.brandsmashers.employee_api.controller;

import com.brandsmashers.employee_api.model.Score;
import com.brandsmashers.employee_api.service.ScoreService;
import org.ff4j.FF4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewController {

  @Autowired private ScoreService scoreService;

  @Autowired private FF4j ff4j;

  @GetMapping("/team/{name}")
  public String viewScore(@PathVariable String name, Model model) {
    if (ff4j.check("live-score-enabled")) {
      Score score = scoreService.getScoreByTeamName(name);
      model.addAttribute("teamName", score.getTeamName());
      model.addAttribute("score", score.getScore());
      return "score";
    } else {
      return "feature-disabled"; // fallback page if feature is off
    }
  }
}
