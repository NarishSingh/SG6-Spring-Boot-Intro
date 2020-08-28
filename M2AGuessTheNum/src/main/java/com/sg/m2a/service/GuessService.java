package com.sg.m2a.service;

import com.sg.m2a.models.Game;
import com.sg.m2a.models.GameVM;
import com.sg.m2a.models.Round;
import com.sg.m2a.models.RoundVM;
import java.util.List;

public interface GuessService {

    /*Regular model methods*/
    /**
     * Start a new Game and generate an answer
     *
     * @return {Game} a fully formed Game obj
     */
    Game newGame();

    /**
     * Play a round of an existing game
     *
     * @param guess  {String} a 4 digit guess from the consumer
     * @param gameId {int} the id of an existing game
     * @return {Round} a fully formed Round obj
     * @throws BadGuessException     if consumer's guess has duplicate entries
     * @throws NotFoundException     if consumer attempts to retrieve a
     *                               non-existing game
     * @throws GameCompleteException if consumer has already completed the game
     */
    Round guess(String guess, int gameId) throws BadGuessException,
            NotFoundException, GameCompleteException;

    /**
     * Retrieve a list of all games, regardless of status
     *
     * @return {List} a list of all games, unprocessed if game is completed, but
     *         if game in-progress then answer data will be hidden
     */
    List<Game> readAllGames();

    /**
     * Retrieve information on a game
     *
     * @param gameId {int} the id number of an existing game
     * @return {Game} the unprocessed obj is game is complete, answer data
     *         hidden if game is in-progress
     * @throws NotFoundException if game does not exist
     */
    Game readGame(int gameId) throws NotFoundException;

    /**
     * Read all rounds for a game
     *
     * @param gameId {int} the id number for an existing game
     * @return {List} all rounds sorted by time
     * @throws NotFoundException if game doesn't exist
     */
    List<Round> readGameRounds(int gameId) throws NotFoundException;

    /*helper methods*/
    /**
     * Validate guess to ensure it has no duplicate digits
     *
     * @param guessAnswer {String} 4 digits entered by player or the answer
     *                    generated
     * @return {String} if valid, return the guess
     * @throws BadGuessException if contains duplicate entries, throw this
     */
    String validateDigitSet(String guessAnswer) throws BadGuessException;

    /*View model methods*/
    /**
     * Convert round model into a view model
     *
     * @param round {Round} well formed obj
     * @return {RoundVM} vm object
     */
    RoundVM convertRound(Round round);

    /**
     * Convert Game model into vm
     *
     * @param game {Game} well formed obj
     * @return {GameVm} vm obj
     * @throws NotFoundException if consumer requests a game that doesn't exist
     */
    GameVM convertGame(Game game) throws NotFoundException;

    /**
     * Convert all Round obj's for one game VM's
     *
     * @param id {int} an existing game id
     * @return {List] all round vm's for one game
     * @throws NotFoundException if game does not exist
     */
    List<RoundVM> getAllGameRoundVM(int id) throws NotFoundException;

    /**
     * Convert all Round obj's into VM's
     *
     * @return {List} all Round VM's
     */
    List<RoundVM> getAllRoundVM();

}
