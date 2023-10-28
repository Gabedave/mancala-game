package com.gabedave.mancalagame.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabedave.mancalagame.models.Game;
import com.gabedave.mancalagame.repository.GameRepository;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    public Game initialiseGame() {
        Game newGame = new Game();

        return gameRepository.save(newGame);
    }

    public List<Game> getActiveGames() {
        return gameRepository.findByWinnerIsNull();
    }

    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }

    public Game playGame(Long id, int player, int playerPitIndex) throws Exception {
        Game game = gameRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Game not found"));

        game.makeMove(player, playerPitIndex);

        return gameRepository.save(game);
    }
}
