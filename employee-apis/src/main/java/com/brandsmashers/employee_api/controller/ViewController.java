package com.brandsmashers.employee_api.controller;

import com.brandsmashers.employee_api.model.Score;
import com.brandsmashers.employee_api.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/team/{name}")
    public String viewScore(@PathVariable String name, Model model) {
        Score score = scoreService.getScoreByTeamName(name);
        model.addAttribute("teamName", score.getTeamName());
        model.addAttribute("score", score.getScore());
        return "score";
    }
}
