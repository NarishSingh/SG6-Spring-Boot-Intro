package com.sg.m2a.data;

import com.sg.m2a.TestApplicationConfiguration;
import com.sg.m2a.models.Game;
import com.sg.m2a.models.Round;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GameDaoTest {
    
    @Autowired
    GameDao gameDao;
    
    @Autowired
    RoundDao roundDao;
    
    static Round r1;
    static Round r1update;
    static Round r2;
    static Round r3;
    static Game g1;
    static Game g2;
    
    public GameDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        final int FIRST_GAME = 1;
        final int SECOND_GAME = 2;
        final String G1_ANSWER = "1234";
        final String G2_ANSWER = "2345";
        
        final String FIRST_GUESS = "2345"; //correct for g2
        final String FIRST_GUESS_UPDATE = "1111"; //fail, 1 exact for g1
        final String SECOND_GUESS = "1234"; //correct for g1
        
        /*round obj's*/
        //g1
        r1 = new Round();
        r1.setRoundId(1);
        r1.setGameId(FIRST_GAME);
        r1.setGuess(FIRST_GUESS);
        r1.setTime(LocalDateTime.MIN);
        r1.setDigitMatches("e:0:p:3"); //fail
        
        r1update = new Round();
        r1update.setRoundId(1);
        r1update.setGameId(FIRST_GAME);
        r1update.setGuess(FIRST_GUESS_UPDATE);
        r1update.setTime(LocalDateTime.MIN.plusMinutes(1));
        r1update.setDigitMatches("e:1:p:0"); //fail
        
        r2 = new Round();
        r2.setRoundId(2);
        r2.setGameId(FIRST_GAME);
        r2.setGuess(SECOND_GUESS);
        r2.setTime(LocalDateTime.MIN.plusMinutes(2));
        r2.setDigitMatches("e:4:p:0"); //pass
        
        //g2
        r3 = new Round();
        r3.setRoundId(3);
        r3.setGameId(SECOND_GAME);
        r3.setGuess(FIRST_GUESS);
        r3.setTime(LocalDateTime.MIN);
        r3.setDigitMatches("e:4:p:0"); //pass
        
        /*game obj's*/
        g1 = new Game();
        g1.setGameId(FIRST_GAME);
        g1.setAnswer(G1_ANSWER);
        g1.setIsFinished(false);
        List<Round> g1Rounds = new ArrayList<>();
        g1Rounds.add(r1);
        g1Rounds.add(r2);
        g1.setRounds(g1Rounds);
        
        g2 = new Game();
        g2.setGameId(SECOND_GAME);
        g2.setAnswer(G2_ANSWER);
        g2.setIsFinished(false);
        List<Round> g2Rounds = new ArrayList<>();
        g2Rounds.add(r3);
        g2.setRounds(g2Rounds);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        //clear db
        List<Round> rounds = roundDao.readAllRounds();
        for (Round round : rounds) {
            roundDao.deleteRoundByID(round.getRoundId());
        }
        
        List<Game> games = gameDao.readAllGames();
        for (Game game : games) {
            gameDao.deleteGameById(game.getGameId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createGame method, of class GameDao.
     */
    @Test
    public void testCreateReadGame() {
        //arrange
        Game game1 = gameDao.createGame(g1);
        Game game2 = gameDao.createGame(g2);
        
        //act
        Game firstFromDao = gameDao.readGameById(g1.getGameId());
        Game secondFromDao = gameDao.readGameById(g2.getGameId());
        
        //assert
        assertEquals(game1, firstFromDao);
        assertEquals(game2, secondFromDao);
    }

    /**
     * Test of readAllGames method, of class GameDao.
     */
    @Test
    public void testReadAllGames() {
        //arrange
        Game game1 = gameDao.createGame(g1);
        Game game2 = gameDao.createGame(g2);
        
        //act
        List<Game> allGames = gameDao.readAllGames();
        
        //assert
        assertEquals(2, allGames.size());
        assertTrue(allGames.contains(game1));
        assertTrue(allGames.contains(game2));
    }

    /**
     * Test of updateGame method, of class GameDao.
     */
    @Test
    public void testUpdateGame() {
        //arrange
        Game g1update = new Game();
        g1update.setGameId(g1.getGameId());
        g1update.setAnswer(g1.getAnswer());
        g1update.setIsFinished(g1.isIsFinished());
        List<Round> g1uRound = new ArrayList<>();
        g1uRound.add(r1update);
        g1update.setRounds(g1uRound);
        
        //act
        Game game1 = gameDao.createGame(g1);
        Game original = gameDao.readGameById(g1.getGameId());
        
        boolean updated = gameDao.updateGame(g1update);
        Game edited = gameDao.readGameById(g1.getGameId());
        
        //assert
        assertTrue(updated);
        assertNotEquals(original, edited);
        assertEquals(original.getGameId(), edited.getGameId());
    }

    /**
     * Test of deleteGameById method, of class GameDao.
     */
    @Test
    public void testDeleteGameById() {
        //arrange
        Game game1 = gameDao.createGame(g1);
        Game game2 = gameDao.createGame(g2);
        
        //act
        List<Game> preDelete = gameDao.readAllGames();
        
        boolean deleted = gameDao.deleteGameById(game1.getGameId());
        List<Game> postDelete = gameDao.readAllGames();
        Game firstFromDao = gameDao.readGameById(game1.getGameId());
        Game secondFromDao = gameDao.readGameById(game2.getGameId());
        
        //assert
        assertEquals(2, preDelete.size());
        assertTrue(deleted);
        assertEquals(1, postDelete.size());
        assertNull(firstFromDao);
        assertNotNull(secondFromDao);
    }

    /**
     * Test of readRoundsForGame method, of class GameDao.
     */
    @Test
    public void testReadRoundsForGame() {
        //arrange
        Game game1 = gameDao.createGame(g1);
        Game game2 = gameDao.createGame(g2);
        
        //act
        List<Round> g1Rounds = gameDao.readRoundsForGame(game1);
        List<Round> g2Rounds = gameDao.readRoundsForGame(game2);
        
        //assert
        assertEquals(2, g1Rounds.size());
        assertTrue(g1Rounds.contains(r1));
        assertTrue(g1Rounds.contains(r2));
        
        assertEquals(1, g2Rounds.size());
        assertTrue(g2Rounds.contains(r3));
    }

}
