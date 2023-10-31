export default class Api {
  static API_URL =
    process.env.REACT_APP_API_URL || "http://localhost:8080/api/v1";

  static getActiveGames = async () => {
    try {
      const response = await fetch(`${this.API_URL}/mancala/active`, {
        method: "GET",
      });
      const data = await response.json();

      return data;
    } catch (error) {
      console.error("Error getting active games", error);
    }
  };

  static startNewGame = async () => {
    try {
      const response = await fetch(`${this.API_URL}/mancala/start-game`, {
        method: "POST",
      });
      const data = await response.json();
      return data;
    } catch (error) {
      console.error("Error starting a new game", error);
    }
  };

  static getGame = async (gameId) => {
    try {
      const response = await fetch(`${this.API_URL}/mancala/${gameId}`);
      const data = await response.json();
      return data;
    } catch (error) {
      console.error("Error getting game", error);
    }
  };

  static playMancala = async ({ gameId, player, playerPitIndex }) => {
    try {
      const response = await fetch(`${this.API_URL}/mancala/${gameId}/play`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ player, playerPitIndex }),
      });
      const data = await response.json();
      return data;
    } catch (error) {
      console.error("Error playing the Mancala game", error);
    }
  };
}
