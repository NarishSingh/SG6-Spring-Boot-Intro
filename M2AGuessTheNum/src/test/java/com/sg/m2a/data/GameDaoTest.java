package com.sg.m2a.data;

import com.sg.m2a.TestApplicationConfiguration;
import com.sg.m2a.models.Game;
import com.sg.m2a.models.Round;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
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
    
    static Game game1;
    static Game game2;
    static Round round1;
    static Round round2;
    static Round round3;
    
    public GameDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        /*clear db*/
        List<Round> rounds = roundDao.readAllRounds();
        for (Round round : rounds) {
            roundDao.deleteRoundByID(round.getRoundId());
        }
        
        List<Game> games = gameDao.readAllGames();
        for (Game game : games) {
            gameDao.deleteGameById(game.getGameId());
        }
        
        /*obj creation*/
        final String G1_ANSWER = "1234";
        final String G2_ANSWER = "2345";
        
        final String FIRST_GUESS = "2345"; //correct for g2
        final String FIRST_GUESS_UPDATE = "1111"; //fail, 1 exact for g1
        final String SECOND_GUESS = "1234"; //correct for g1
        
        /*game obj's*/
        Game g1 = new Game();
        g1.setAnswer(G1_ANSWER);
        g1.setIsFinished(false);
        
        Game g2 = new Game();
        g2.setAnswer(G2_ANSWER);
        g2.setIsFinished(false);

        /*game creation*/
        game1 = gameDao.createGame(g1);
        game2 = gameDao.createGame(g2);
        
        /*round obj's*/
        //g1
        Round r1 = new Round();
        r1.setGameId(game1.getGameId());
        r1.setGuess(FIRST_GUESS);
        r1.setDigitMatches("e:0:p:3"); //fail
        
        Round r1update = new Round();
        r1update.setRoundId(1);
        r1update.setGameId(game1.getGameId());
        r1update.setGuess(FIRST_GUESS_UPDATE);
        r1update.setDigitMatches("e:1:p:0"); //fail
        
        Round r2 = new Round();
        r2.setRoundId(2);
        r2.setGameId(game1.getGameId());
        r2.setGuess(SECOND_GUESS);
        r2.setDigitMatches("e:4:p:0"); //pass
        
        //g2
        Round r3 = new Round();
        r3.setRoundId(3);
        r3.setGameId(game2.getGameId());
        r3.setGuess(FIRST_GUESS);
        r3.setDigitMatches("e:4:p:0"); //pass
        
        /*round creation*/
        round1 = roundDao.createRound(r1);
        round2 = roundDao.createRound(r2);
        round3 = roundDao.createRound(r3);
        
        /*round addition*/
        List<Round> g1Rounds = new ArrayList<>();
        g1Rounds.add(round1);
        g1Rounds.add(round2);
        game1.setRounds(g1Rounds);

        List<Round> g2Rounds = new ArrayList<>();
        g2Rounds.add(round3);
        game2.setRounds(g2Rounds);
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of createGame method, of class GameDao.
     */
    @Test
    public void testCreateGame() {
        //arrange
        Game g = new Game();
        g.setAnswer("9999");
        
        //act
        Game newGame = gameDao.createGame(g);
        
        //assert
        assertNotNull(newGame);
        assertEquals(newGame.getAnswer(), g.getAnswer());
        assertEquals(newGame.isIsFinished(), false);
    }

    /**
     * Test of readGameById method, of class GameDao.
     */
    @Test
    public void testReadGameById() {
        //arrange done in setup
        
        //act
        Game firstFromDao = gameDao.readGameById(game1.getGameId());
        Game secondFromDao = gameDao.readGameById(game2.getGameId());
        
        //assert
        assertEquals(game1, firstFromDao);
        assertEquals(game2, secondFromDao);
    }

    /**
     * Test of readAllGames method, of class GameDao.
     */
    @Test
    public void testReadAllGames() {
        //arrange done in setup
        
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
