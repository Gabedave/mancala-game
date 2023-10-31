package com.gabedave.mancalagame.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GameTest {

    private Game game;

    @BeforeEach
    public void setup() {
        game = new Game();
    }

    @Test
    public void testCreateGameStructure() {
        Map<String, List<Integer>> gameStructure = game.getGame_structure();
        Assertions.assertNotNull(gameStructure);
        Assertions.assertEquals(2, gameStructure.size());
        Assertions.assertTrue(gameStructure.containsKey("player1"));
        Assertions.assertTrue(gameStructure.containsKey("player2"));

        List<Integer> player1Pits = gameStructure.get("player1");
        List<Integer> player2Pits = gameStructure.get("player2");

        Assertions.assertEquals(7, player1Pits.size());
        Assertions.assertEquals(7, player2Pits.size());

        Assertions.assertEquals(Arrays.asList(6, 6, 6, 6, 6, 6, 0), player1Pits);
        Assertions.assertEquals(Arrays.asList(6, 6, 6, 6, 6, 6, 0), player2Pits);
    }

    @Test
    public void testMakeMoveValidMove() throws Exception {
        game.setGame_structure(Map.of("player1", Arrays.asList(6, 6, 6, 6, 6, 6, 0),
                "player2", Arrays.asList(6, 6, 6, 6, 6, 6, 0)));
        game.setPlayer_turn(1);

        game.makeMove(1, 0);

        Map<String, List<Integer>> gameStructure = game.getGame_structure();
        Assertions.assertNotNull(gameStructure);

        List<Integer> player1Pits = gameStructure.get("player1");
        List<Integer> player2Pits = gameStructure.get("player2");

        Assertions.assertEquals(Arrays.asList(0, 7, 7, 7, 7, 7, 1), player1Pits);
        Assertions.assertEquals(Arrays.asList(6, 6, 6, 6, 6, 6, 0), player2Pits);
    }

    @Test
    public void testMakeMoveInvalidMove() {
        game.setGame_structure(Map.of("player1", Arrays.asList(6, 6, 6, 6, 6, 6, 0),
                "player2", Arrays.asList(6, 6, 6, 6, 6, 6, 0)));
        game.setPlayer_turn(1);

        Assertions.assertThrows(Exception.class, () -> game.makeMove(1, 6));
        Assertions.assertThrows(Exception.class, () -> game.makeMove(2, 6));
    }

    @Test
    public void testCheckForAndSetWinnerPlayer1Wins() throws Exception {
        game.setGame_structure(Map.of("player1", Arrays.asList(0, 0, 0, 0, 0, 1, 41),
                "player2", Arrays.asList(6, 6, 6, 6, 6, 0, 0)));
        game.setPlayer_turn(1);

        game.makeMove(1, 5);

        Assertions.assertEquals(1, game.getWinner());
    }

    @Test
    public void testCheckForAndSetWinnerPlayer2Wins() throws Exception {
        game.setGame_structure(Map.of("player1", Arrays.asList(6, 6, 6, 6, 6, 2, 0),
                "player2", Arrays.asList(0, 0, 0, 0, 0, 1, 39)));
        game.setPlayer_turn(2);

        game.makeMove(2, 5);

        Assertions.assertEquals(2, game.getWinner());
    }

    @Test
    public void testStonesCaptureByPlayer1() throws Exception {
        game.setGame_structure(Map.of("player1", Arrays.asList(0, 1, 0, 0, 0, 0, 35),
                "player2", Arrays.asList(6, 6, 6, 6, 6, 6, 0)));

        game.setPlayer_turn(1);

        game.makeMove(1, 1);

        Map<String, List<Integer>> gameStructure = game.getGame_structure();
        Assertions.assertNotNull(gameStructure);

        List<Integer> player1Pits = gameStructure.get("player1");
        List<Integer> player2Pits = gameStructure.get("player2");

        Assertions.assertEquals(Arrays.asList(0, 0, 0, 0, 0, 0, 42), player1Pits);
        Assertions.assertEquals(Arrays.asList(6, 6, 6, 0, 6, 6, 0), player2Pits);
    }

    @Test
    public void testStonesCaptureByPlayer2() throws Exception {
        game.setGame_structure(Map.of("player2", Arrays.asList(1, 0, 0, 0, 0, 0, 35),
                "player1", Arrays.asList(6, 6, 6, 6, 6, 6, 0)));

        game.setPlayer_turn(2);

        game.makeMove(2, 0);

        Map<String, List<Integer>> gameStructure = game.getGame_structure();
        Assertions.assertNotNull(gameStructure);

        List<Integer> player1Pits = gameStructure.get("player1");
        List<Integer> player2Pits = gameStructure.get("player2");

        Assertions.assertEquals(Arrays.asList(0, 0, 0, 0, 0, 0, 42), player2Pits);
        Assertions.assertEquals(Arrays.asList(6, 6, 6, 6, 0, 6, 0), player1Pits);
    }
}