CREATE TABLE GAME (
ID INT PRIMARY KEY AUTO_INCREMENT,
GAME_STRUCTURE JSON NOT NULL,
PLAYER_TURN INT NOT NULL,
WINNER INT
);