package com.sg.m2a.service;

import com.sg.m2a.data.GameDao;
import com.sg.m2a.data.RoundDao;
import com.sg.m2a.models.Game;
import com.sg.m2a.models.Round;
import com.sg.m2a.models.RoundVM;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuessService {

    @Autowired
    RoundDao roundDao;

    @Autowired
    GameDao gameDao;

    /*Regular model methods*/
    /**
     * Start a new Game and generate an answer
     *
     * @return {Game} a fully formed Game obj,
     */
    public Game newGame() {
        Game newGame = new Game();

        //generate answer, hashset guranteed to only keep distinct numbers
        Random rng = new Random();
        Set<Integer> ansSet = new HashSet<>();
        while (ansSet.size() < 4) {
            ansSet.add(rng.nextInt(10));
        }

        newGame.setAnswer(ansSet.toString());

        return gameDao.createGame(newGame);
    }

    /**
     * Play a round of an existing game
     *
     * @param guess  {String} a 4 digit guess from the consumer
     * @param gameId {int} the id of an existing game
     * @return {Round} a fully formed Round obj
     * @throws DuplicateDigitEntryException if consumer's guess has duplicate
     *                                      entries
     * @throws NotFoundException            if consumer attempts to retrieve a
     *                                      non-existing game
     */
    public Round guess(String guess, int gameId) throws DuplicateDigitEntryException,
            NotFoundException {
        validateGuess(guess);

        /*round creation*/
        Round round = new Round();
        round.setGuess(guess);
        round.setGameId(gameId);

        /*results -  if correct, mark game as finished**/
        Game game = gameDao.readGameById(gameId);
        if (game == null) {
            throw new NotFoundException("Game not found");
        }

        if (game.getAnswer().equals(round.getGuess())) {
            game.setIsFinished(true);
            round.setDigitMatches("e:4:p:0");
        } else {
            int exactCount = 0;
            int partialCount = 0;

            for (int i = 0; i < guess.length(); i++) {
                if (guess.charAt(i) == game.getAnswer().charAt(i)) {
                    exactCount++;
                } else if (guess.contains(String.valueOf(game.getAnswer().charAt(i)))
                        && guess.charAt(i) != game.getAnswer().charAt(i)) {
                    partialCount++;
                }
            }

            String resultString = String.format("e:%s:p:%s", exactCount, partialCount);
            round.setDigitMatches(resultString);
            game.setIsFinished(false);
        }

        /*update game, regardless*/
        Round newRound = roundDao.createRound(round);
        gameDao.updateGame(game); //updates status and associates rounds

        return newRound;
    }

    /**
     * Validate guess to ensure it has no duplicate digits
     *
     * @param guess {String} 4 digits entered by player
     * @return {String} if valid, return the guess
     * @throws DuplicateDigitEntryException if contains duplicate entries, throw
     *                                      this
     */
    private String validateGuess(String guess) throws DuplicateDigitEntryException {
        Set<Integer> guessSet = new TreeSet<>();
        for (int i = 0; i < guess.length(); i++) {
            guessSet.add(Integer.valueOf(guess.charAt(i)));
        }

        if (guessSet.size() != 4) {
            throw new DuplicateDigitEntryException("No duplicate digits allowed.");
        } else {
            return guess;
        }
    }

    /*View model methods*/
    /**
     * Convert all Round obj's into VM's
     *
     * @return {List} all Round VM's
     */
    public List<RoundVM> getAllRoundVM() {
        List<Round> rounds = roundDao.readAllRounds();
        List<RoundVM> roundVMs = new ArrayList<>();

        for (Round r : rounds) {
            roundVMs.add(convert(r));
        }

        return roundVMs;
    }

    /**
     * Convert round model into a view model
     *
     * @param round {Round} well formed obj
     * @return {RoundVM} vm object
     */
    private RoundVM convert(Round round) {
        RoundVM roundVM = new RoundVM();

        //guess
        String guessPrompt = "You guessed - ";
        guessPrompt += round.getGuess();
        guessPrompt += ".";
        roundVM.setGuess(guessPrompt);

        //matches
        char exactCount = round.getDigitMatches().charAt(2);
        char partialCount = round.getDigitMatches().charAt(6);

        String matchesPrompt = "This contains ";

        if (exactCount == '0' && partialCount == '0') {
            matchesPrompt += "no digit matches...";
        } else {
            if (exactCount != '0') {
                matchesPrompt += exactCount;
                matchesPrompt += " exact matches";
            }

            if (exactCount != '0' && partialCount != '0') {
                matchesPrompt += " and ";
            }

            if (partialCount != '0') {
                matchesPrompt += partialCount;
                matchesPrompt += " partial matches";
            }

            matchesPrompt += "!";
        }

        roundVM.setRoundResult(matchesPrompt);

        return roundVM;
    }

}
