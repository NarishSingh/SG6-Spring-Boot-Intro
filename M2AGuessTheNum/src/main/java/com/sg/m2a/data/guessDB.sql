/**
 * Author:  naris
 * Created: Aug 19, 2020
 */

DROP DATABASE IF EXISTS guessDB;
CREATE DATABASE guessDB;
USE guessDB;

CREATE TABLE game(
    gameId INT PRIMARY KEY AUTO_INCREMENT,
    answer CHAR(4) NOT NULL,
    isFinished BIT NOT NULL DEFAULT 0
);

CREATE TABLE round(
    roundId INT PRIMARY KEY AUTO_INCREMENT,
    gameId INT NOT NULL,
    guess CHAR(4) NOT NULL,
    `time` DATETIME NOT NULL DEFAULT NOW(),
    digitMatches CHAR(7) NOT NULL,

    CONSTRAINT `fk_Round_Game` FOREIGN KEY (gameId)
        REFERENCES game (gameId)
);