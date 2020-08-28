package com.sg.m2a.controllers;

import com.sg.m2a.models.Game;
import com.sg.m2a.models.GameVM;
import com.sg.m2a.models.Round;
import com.sg.m2a.models.RoundVM;
import com.sg.m2a.service.BadGuessException;
import com.sg.m2a.service.GameCompleteException;
import com.sg.m2a.service.GuessServiceImpl;
import com.sg.m2a.service.NotFoundException;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guessthenumber/vm")
public class GuessVMController {

    @Autowired
    GuessServiceImpl serv;

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
     * @param round {Round} a round object, requires values for "guess" as a
     *              String and "gameId" as an int
     * @return {Round} the Round obj
     * @throws BadGuessException     if a guess has duplicate digits
     * @throws NotFoundException     if consumer requests a game that doesn't
     *                               exist
     * @throws GameCompleteException if consumer attempts to play after a game
     *                               is completed
     */
    @PostMapping("/guess")
    @ResponseStatus(HttpStatus.CREATED)
    public RoundVM checkGuess(@RequestBody Round round)
            throws BadGuessException, NotFoundException, GameCompleteException {
        try {
            Round r = serv.guess(round.getGuess(), round.getGameId());
            return serv.convertRound(r);
        } catch (BadGuessException e) {
            throw new BadGuessException(e.getMessage(), e);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage(), e);
        } catch (GameCompleteException e) {
            throw new GameCompleteException(e.getMessage(), e);
        }
    }

    /**
     * Get a list of all games
     *
     * @return {List} all in-progress or completed games, only complete games
     *         will show their answers
     * @throws NotFoundException if game doesn't exist
     */
    @GetMapping("/game")
    public List<GameVM> getAllGames() throws NotFoundException {
        List<Game> allGames = serv.readAllGames();
        List<GameVM> allGameVMs = new ArrayList<>();

        for (Game game : allGames) {
            allGameVMs.add(serv.convertGame(game));
        }

        return allGameVMs;

    }

    /**
     * Get an in progress or completed game
     *
     * @param gameId {int} gameId of a existing game
     * @return {Game} the obj stored at that id
     * @throws NotFoundException if consumer requests a game that doesn't exist
     */
    @GetMapping("/game/{gameId}")
    public GameVM getGameVMById(@PathVariable int gameId) throws NotFoundException {
        try {
            Game game = serv.readGame(gameId);

            return serv.convertGame(game);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage(), e);
        }
    }

    /**
     * Get all rounds for an in progress or completed game
     *
     * @param gameId {int} gameId of a existing game
     * @return {List} a game's rounds as vm's sorted by time
     * @throws NotFoundException if consumer requests a game that doesn't exist
     */
    @GetMapping("/rounds/{gameId}")
    public List<RoundVM> getGameRounds(@PathVariable int gameId) throws NotFoundException {
        try {
            return serv.getAllGameRoundVM(gameId);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage(), e);
        }
    }
}
