package com.sg.m2a.service;

import com.sg.m2a.data.GameDao;
import com.sg.m2a.data.RoundDao;
import com.sg.m2a.models.Game;
import com.sg.m2a.models.Round;
import com.sg.m2a.models.RoundVM;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuessServiceImpl implements GuessService {

    @Autowired
    RoundDao roundDao;

    @Autowired
    GameDao gameDao;

    @Autowired
    public GuessServiceImpl(RoundDao roundDao, GameDao gameDao) {
        this.roundDao = roundDao;
        this.gameDao = gameDao;
    }

    /*Regular model methods*/
    @Override
    public Game newGame() {
        Game newGame = new Game();

        //generate answer, hashset guranteed to only keep distinct numbers
        Random rng = new Random();
        Set<Integer> ansSet = new HashSet<>();
        while (ansSet.size() < 4) {
            ansSet.add(rng.nextInt(10));
        }

        String ansString = "";
        for (Integer i : ansSet) {
            ansString += i;
        }
        newGame.setAnswer(ansString);

        return gameDao.createGame(newGame);
    }

    @Override
    public Round guess(String guess, int gameId) throws DuplicateDigitEntryException,
            NotFoundException, GameCompleteException {
        /*param verifications*/
        Game game = gameDao.readGameById(gameId);
        if (game == null) {
            throw new NotFoundException("Game doesn't exist");
        }

        if (game.isIsFinished()) {
            throw new GameCompleteException("Player already won - start a new game");
        }

        validateDigitSet(guess);

        /*round creation*/
        Round round = new Round();
        round.setGuess(guess);
        round.setGameId(gameId);

        /*results -  if correct, mark game as finished*/
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

        /*update game obj + game/round db's regardless of status*/
        Round newRound = roundDao.createRound(round);
        gameDao.updateGame(game); //updates status and associates rounds

        return newRound;
    }

    @Override
    public List<Game> readAllGames() {
        List<Game> allGames = gameDao.readAllGames();
        List<Game> screenedGameList = new ArrayList<>();

        for (Game game : allGames) {
            if (game.isIsFinished()) {
                screenedGameList.add(game);
            } else {
                screenedGameList.add(screenInProgressGame(game)); //hide answer
            }
        }

        return screenedGameList;
    }

    @Override
    public Game readGame(int gameId) throws NotFoundException {
        Game game = gameDao.readGameById(gameId);

        if (game == null) {
            throw new NotFoundException("Game doesn't exist");
        } else if (game.isIsFinished()) {
            return game;
        } else {
            return screenInProgressGame(game); //hide answer
        }
    }

    @Override
    public List<Round> readGameRounds(int gameId) throws NotFoundException {
        Game game = gameDao.readGameById(gameId);
        if (game == null) {
            throw new NotFoundException("Game doesn't exist");
        }

        List<Round> rounds = gameDao.associateRoundsWithGame(game);

        List<Round> sortedTime = rounds.stream()
                .sorted(Comparator.comparing(Round::getTime))
                .collect(Collectors.toList());

        return sortedTime;
    }

    /*helper methods*/
    @Override
    public String validateDigitSet(String guess) throws DuplicateDigitEntryException {
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
     * Create VM to hide the answer of an in-progress game
     *
     * @param game {Game} and in-progress game obj
     * @return {Game} a VM of the obj with its answer hidden
     */
    private Game screenInProgressGame(Game game) {
        Game inProgress = new Game();
        inProgress.setGameId(game.getGameId());
        inProgress.setAnswer("****"); //hide answer
        inProgress.setIsFinished(game.isIsFinished());
        inProgress.setRounds(game.getRounds());

        return inProgress;
    }

    @Override
    public RoundVM convert(Round round) {
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
            //exact
            if (exactCount == '1') {
                matchesPrompt += exactCount;
                matchesPrompt += " exact match";
            } else if (exactCount != '0') {
                matchesPrompt += exactCount;
                matchesPrompt += " exact matches";
            }

            if (exactCount != '0' && partialCount != '0') {
                matchesPrompt += " and ";
            }

            //partial
            if (partialCount == '1') {
                matchesPrompt += partialCount;
                matchesPrompt += " partial match";
            } else if (partialCount != '0') {
                matchesPrompt += partialCount;
                matchesPrompt += " partial matches";
            }

            matchesPrompt += "!";
        }

        roundVM.setRoundResult(matchesPrompt);

        return roundVM;
    }

    @Override
    public List<RoundVM> getAllRoundVM() {
        List<Round> rounds = roundDao.readAllRounds();
        List<RoundVM> roundVMs = new ArrayList<>();

        for (Round r : rounds) {
            roundVMs.add(convert(r));
        }

        return roundVMs;
    }

    @Override
    public List<RoundVM> getAllGameRoundVM(int id) throws NotFoundException {
        Game game = gameDao.readGameById(id);
        if (game == null) {
            throw new NotFoundException("Game doesn't exist");
        }

        List<Round> rounds = gameDao.associateRoundsWithGame(game);
        List<RoundVM> roundVMs = new ArrayList<>();

        for (Round r : rounds) {
            roundVMs.add(convert(r));
        }

        return roundVMs;
    }

}
