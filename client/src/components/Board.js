import React, { useState, useEffect, useCallback, useRef } from "react";
import Api from "../api/api";
import Pit from "./Pit";
import "../Board.css";

const { startNewGame, getActiveGames, playMancala, getGame } = Api;

function MancalaGame() {
  const [gameId, setGameId] = useState(null);
  const [gameStructure, setGameStructure] = useState(null);
  const [currentPlayer, setCurrentPlayer] = useState(1);
  const [winner, setWinner] = useState(null);
  const [activeGames, setActiveGames] = useState(null);
  const [player1ClassNames, setPlayer1ClassNames] = useState("");
  const [player2ClassNames, setPlayer2ClassNames] = useState("");

  useEffect(() => {
    init();
  }, []);

  useEffect(() => {
    const activePlayerClassNames = "player-side";
    const inactivePlayerClassNames = "disable-player player-side";

    if (currentPlayer === 1) {
      setPlayer1ClassNames(activePlayerClassNames);
      setPlayer2ClassNames(inactivePlayerClassNames);
    } else {
      setPlayer1ClassNames(inactivePlayerClassNames);
      setPlayer2ClassNames(activePlayerClassNames);
    }
  }, [currentPlayer]);

  const init = useCallback(() => {
    // Fetch active games when component mounts
    getActiveGames().then((data) => {
      setActiveGames(data);
    });
  }, []);

  const setGameInState = (game) => {
    setGameId(game.id);
    setGameStructure(game.game_structure);
    setCurrentPlayer(game.player_turn);
    setWinner(game.winner);
  };

  const startNewMancalaGame = () => {
    startNewGame().then((data) => {
      setGameInState(data);
    });
  };

  const openGame = (gameId) => {
    getGame(gameId).then((data) => {
      setGameInState(data);
    });
  };

  const playGame = (pitIndex) => {
    if (!gameId || winner) {
      return;
    }

    playMancala({
      gameId,
      player: currentPlayer,
      playerPitIndex: pitIndex,
    }).then((data) => {
      setGameInState(data);
    });
  };

  console.log({ player1ClassNames, player2ClassNames });

  return (
    <div className="container">
      <h1 className="mt-4 mb-4">Mancala Game{gameId && ` #${gameId}`}</h1>
      {!gameId &&
        !winner &&
        activeGames?.length &&
        activeGames.map((game, index) => (
          <ul key={index}>
            <span
              style={{ fontSize: "1rem" }}
              onClick={() => openGame(game.id)}
            >
              {game.id}
            </span>
          </ul>
        ))}
      <button className="btn btn-success mb-4" onClick={startNewMancalaGame}>
        Start New Game
      </button>
      {gameStructure && (
        <div>
          <h2>Current Player: Player {currentPlayer}</h2>
          <div className="board">
            {gameStructure && (
              <>
                <div className={player1ClassNames}>
                  <div>Player 1</div>
                  {gameStructure?.[`player1`]?.map((value, index) => {
                    if (index == 6) {
                      return (
                        <div key={index} className="mancala-wrapper">
                          <div>Mancala: {gameStructure["player1"][6]}</div>
                          <div key={index + 7} className="mancala-store">
                            {[...Array(gameStructure["player1"][6]).keys()].map(
                              (index) => {
                                return (
                                  <div
                                    key={index}
                                    className={
                                      value > 10 ? "small-stone" : "stone"
                                    }
                                  ></div>
                                );
                              }
                            )}
                          </div>
                        </div>
                      );
                    }

                    return (
                      <Pit
                        key={index}
                        index={index}
                        value={value}
                        onClick={playGame}
                      />
                    );
                  })}
                </div>

                <div className={player2ClassNames}>
                  <div>Player 2</div>
                  {[...gameStructure?.[`player2`]]
                    .reverse()
                    ?.map((value, index) => {
                      if (index === 0) {
                        return (
                          <div key={index + 7} className="mancala-wrapper">
                            <div>Mancala: {gameStructure["player2"][6]}</div>
                            <div key={index + 7} className="mancala-store">
                              {[
                                ...Array(gameStructure["player2"][6]).keys(),
                              ].map((index) => {
                                return (
                                  <div
                                    key={index}
                                    className={
                                      value > 10 ? "small-stone" : "stone"
                                    }
                                  ></div>
                                );
                              })}
                            </div>
                          </div>
                        );
                      }
                      return (
                        <Pit
                          key={index + 7}
                          index={6 - index}
                          value={value}
                          onClick={playGame}
                        />
                      );
                    })}
                </div>
              </>
            )}
          </div>
          {winner && <p className="mt-4">Winner: Player {winner}</p>}
        </div>
      )}
    </div>
  );
}

export default MancalaGame;
