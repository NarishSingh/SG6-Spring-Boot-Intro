package com.sg.m2a.controllers;

import com.sg.m2a.models.Game;
import com.sg.m2a.models.Round;
import com.sg.m2a.service.DuplicateDigitEntryException;
import com.sg.m2a.service.GuessService;
import com.sg.m2a.service.NotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guessthenumber")
public class GuessController {

    @Autowired
    GuessService serv;

    /**
     * Starts a new game
     *
     * @return {int} the new gameId
     */
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int startGame() {
        Game beginGame = serv.newGame();

        return beginGame.getGameId();
    }

    /**
     * Play a round - consumer tries to guess the number
     *
     * @param guess  {String} guess with 4 unique digits passed in through JSON
     * @param gameId {int} game's id number passed in through JSON
     * @return {Round} the Round obj
     * @throws DuplicateDigitEntryException if a guess has duplicate digits
     * @throws NotFoundException            if game does not exist
     */
    @PostMapping("/guess")
    public Round checkGuess(@RequestBody String guess, @RequestBody int gameId)
            throws DuplicateDigitEntryException, NotFoundException {
        try {
            return serv.guess(guess, gameId);
        } catch (DuplicateDigitEntryException e) {
            throw new DuplicateDigitEntryException("Bad guess - No duplicate digits allowed", e);
        } catch (NotFoundException e) {
            throw new NotFoundException("Game doesn't exist", e);
        }
    }

    /**
     * Get a list of all games
     *
     * @return {List} all in-progress or completed games, only complete games
     *         will show their answers
     */
    @GetMapping("game")
    public List<Game> getAllGames() {
        //list of all games

        //if game is finished, show answer
        //else, no answer
    }

    /**
     * Get an in progress or completed game
     *
     * @param id {int} gameId of a existing game
     * @return {Game} the obj stored at that id
     */
    @GetMapping("game/{gameId}")
    public Game getGameById(@PathVariable int id) {
        //read the game at the id

        //if game is finished, show answer
        //else, no answer
    }

    /**
     * Get all rounds for an in progress or completed game
     *
     * @param id {int} gameId of a existing game
     * @return {List} a game's rounds sorted by time
     */
    @GetMapping("rounds/{gameId}")
    public List<Round> getGameRounds(@PathVariable int id) {
        //get all rounds at the game id
        //make sure they are all sorted by time
    }

}
