package com.brandsmashers.employee_api.controller;

import com.brandsmashers.employee_api.model.Score;
import com.brandsmashers.employee_api.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ScoreWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ScoreService scoreService;

    @PostMapping("/api/update-score")
    @ResponseBody
    public ResponseEntity<?> updateScore(@RequestParam String teamName, @RequestParam int score) {
        Score updated = scoreService.updateScore(teamName, score);
        messagingTemplate.convertAndSend("/topic/score/" + teamName, updated);
        return ResponseEntity.ok(updated);
    }
}
