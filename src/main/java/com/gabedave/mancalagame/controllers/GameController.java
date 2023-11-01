package com.gabedave.mancalagame.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabedave.mancalagame.controllers.interfaces.PlayGameParams;
import com.gabedave.mancalagame.models.Game;
import com.gabedave.mancalagame.services.GameService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/mancala")
public class GameController {
    @Autowired
    private GameService gameService;

    @PostMapping("/start-game")
    public ResponseEntity<Game> startGame() {
        return new ResponseEntity<Game>(gameService.initialiseGame(), HttpStatus.CREATED);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Game>> getActiveGames() {
        return new ResponseEntity<List<Game>>(gameService.getActiveGames(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Game>> getSingleGame(@PathVariable Long id) {
        return new ResponseEntity<Optional<Game>>(gameService.getGameById(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/play")
    public ResponseEntity<Game> playGame(@PathVariable Long id, @RequestBody @Valid PlayGameParams payload)
            throws Exception {
        return new ResponseEntity<Game>(
                gameService.playGame(id, payload.getPlayer(), payload.getPlayerPitIndex()),
                HttpStatus.ACCEPTED);
    }
}
