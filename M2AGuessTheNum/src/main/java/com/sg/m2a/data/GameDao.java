package com.sg.m2a.data;

import com.sg.m2a.models.Game;
import com.sg.m2a.models.Round;
import java.util.List;

public interface GameDao {

    /**
     * Start a new game
     *
     * @param newGame {Game} new game to be added to db
     * @return {Game} the successfully added game
     */
    Game createGame(Game newGame);

    /**
     * Find a game by its id
     *
     * @param id {int} a game's id num, >0
     * @return {Game} the game from db corresponding to that pk
     */
    Game readGameById(int id);

    /**
     * Find all games to date stored in db
     *
     * @return {List} all games played until that point
     */
    List<Game> readAllGames();

    /**
     * Update an existing game
     *
     * @param game {Game} the replacement obj to be stored
     * @return {boolean} true if game exists and is updated
     */
    boolean updateGame(Game game);

    /**
     * Delete an existing game
     *
     * @param id {int} a game's id num, >0
     * @return {boolean} true if item exists and is deleted
     */
    boolean deleteGameById(int id);

    /**
     * Read all rounds played for a game
     *
     * @param game {Game} a in-progress or completed game
     * @return {List} all rounds played to date for a game
     */
    List<Round> readRoundsForGame(Game game);
    
}
