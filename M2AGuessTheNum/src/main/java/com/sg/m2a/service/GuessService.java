package com.sg.m2a.service;

import com.sg.m2a.data.GameDao;
import com.sg.m2a.data.RoundDao;
import com.sg.m2a.models.Game;
import com.sg.m2a.models.Round;
import com.sg.m2a.models.RoundVM;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
