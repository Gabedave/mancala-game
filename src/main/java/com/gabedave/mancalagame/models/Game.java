package com.gabedave.mancalagame.models;

import java.util.*;

import com.gabedave.mancalagame.repository.converters.CustomJSONConverter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Game stucture is required")
    @Convert(converter = CustomJSONConverter.class)
    private Map<String, List<Integer>> game_structure = createGameStructure();

    @NotNull(message = "Player turn must be either 1 or 2")
    private Integer player_turn = 1;

    private Integer winner;

    private Map<String, List<Integer>> createGameStructure() {
        HashMap<String, List<Integer>> game_structure = new HashMap<String, List<Integer>>();

        game_structure.put("player1", initializePlayerPits());
        game_structure.put("player2", initializePlayerPits());

        return game_structure;
    }

    private List<Integer> initializePlayerPits() {
        // Setting the initial number of stones to 6 stones in each pit and 0 in the
        // mancala
        return List.of(6, 6, 6, 6, 6, 6, 0);
    }

    // Helper method: a player plays the game
    public void makeMove(int player, int playerPitIndex) throws Exception {
        if (!canMakeValidMove(player, playerPitIndex)) {
            throw new Exception("Invalid Move");
        }

        int pitIndex = player == 1 ? playerPitIndex : playerPitIndex + 7;
        int totalPits = 14;

        List<Integer> allPits = createPitsFromGameStructure(totalPits);

        int playerMancalaIndex = ((totalPits / 2) * player) - 1;
        int opponentMancalaIndex = ((totalPits / 2) + playerMancalaIndex) % totalPits;

        // move logic
        int stonesToMove = allPits.get(pitIndex); // Get the stones from the selected pit
        allPits.set(pitIndex, 0); // Empty the selected pit

        // Distribute the stones to the next pits
        while (stonesToMove > 0) {
            pitIndex = (pitIndex + 1) % totalPits;

            if (pitIndex == opponentMancalaIndex) {
                continue;
            }

            allPits.set(pitIndex, allPits.get(pitIndex) + 1);
            stonesToMove--;
        }

        // Example: Check if the last stone lands in an empty pit on the player's side
        int oppPitIndex = oppositePitIndex(pitIndex, totalPits);
        if (stonesToMove == 0 && pitIndex != playerMancalaIndex && allPits.get(pitIndex) == 1
                && allPits.get(oppPitIndex) > 0) {
            // Capture the stones from the opposite pit and the last stone
            int capturedStones = allPits.get(oppPitIndex) + 1;
            allPits.set(oppPitIndex, 0);
            allPits.set(pitIndex, 0);
            allPits.set(playerMancalaIndex, allPits.get(playerMancalaIndex) + capturedStones);
        }

        // Update game state and turn
        game_structure = createGameStructureFromPits(totalPits, allPits);
        player_turn = player == 1 ? 2 : 1;

        // Check for end game conditions, determine the winner, etc.
        checkForAndSetWinner(totalPits, allPits);
    }

    private void checkForAndSetWinner(int totalPits, List<Integer> allPits) {

        int player1MancalaIndex = (totalPits / 2) - 1;
        int player2MancalaIndex = totalPits - 1;

        // Check if one side has all pits empty
        int player1Stones = sumStones(allPits.subList(0, player1MancalaIndex));
        int player2Stones = sumStones(allPits.subList(player1MancalaIndex + 1, player2MancalaIndex));

        if (player1Stones > 0 && player2Stones > 0) {
            return;
        }

        int player1Score = allPits.get(player1MancalaIndex) + player1Stones;
        int player2Score = allPits.get(player2MancalaIndex) + player2Stones;

        if (player1Score > player2Score) {
            this.winner = 1;
        } else if (player2Score > player1Score) {
            this.winner = 2;
        } else {
            this.winner = 0;
        }
    }

    private List<Integer> createPitsFromGameStructure(int totalPits) {
        int midPoint = totalPits / 2;
        List<Integer> allPits = new ArrayList<Integer>(totalPits);

        List<Integer> player1Pits = game_structure.get("player1");
        List<Integer> player2Pits = game_structure.get("player2");

        for (int i = 0; i < player1Pits.size(); i++) {
            allPits.add(i, player1Pits.get(i));
        }
        for (int i = 0; i < player2Pits.size(); i++) {
            allPits.add(i + midPoint, player2Pits.get(i));
        }

        return allPits;
    }

    private Map<String, List<Integer>> createGameStructureFromPits(int totalPits, List<Integer> allPits) {
        int midPoint = totalPits / 2;
        HashMap<String, List<Integer>> gameStructure = new HashMap<String, List<Integer>>();

        gameStructure.put("player1", allPits.subList(0, midPoint));
        gameStructure.put("player2", allPits.subList(midPoint, totalPits));

        return gameStructure;
    }

    private boolean canMakeValidMove(int activePlayer, int pitIndex) {
        int stonesInPit = game_structure.get(activePlayer == 1 ? "player1" : "player2").get(pitIndex);

        if (activePlayer == player_turn && winner == null && pitIndex >= 0 && pitIndex < 6 && stonesInPit > 0)
            return true;
        return false;
    }

    private int sumStones(List<Integer> pits) {
        int sum = 0;
        for (int i : pits) {
            sum += i;
        }
        return sum;
    }

    private int oppositePitIndex(int pitIndex, int totalPits) {
        // Calculate the index of the opposite pit
        // This assumes the pits are arranged in a circular manner
        return totalPits - pitIndex - 2;
    }
}