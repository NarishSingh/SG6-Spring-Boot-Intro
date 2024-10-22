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
     * Read a game from db and associate its rounds
     *
     * @param id {int} a game's id number, >0
     * @return {Game} the game from db corresponding to that pk, null if game
     *         doesn't exist
     */
    Game readGameById(int id);

    /**
     * Read all games in db and associate their rounds
     *
     * @return {List} all active or completed games played
     */
    List<Game> readAllGames();

    /**
     * Update an existing game's status in db and associate its rounds to obj in
     * memory
     *
     * @param game {Game} a in-progress or completed game
     * @return {boolean} true if game exists and is updated in db
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
     * Load all rounds played for a game to memory
     *
     * @param game {Game} an in-progress or completed game
     * @return {List} all rounds played to date for a game
     */
    List<Round> associateRoundsWithGame(Game game);

}
