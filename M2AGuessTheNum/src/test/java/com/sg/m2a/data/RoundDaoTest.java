package com.sg.m2a.data;

import com.sg.m2a.TestApplicationConfiguration;
import com.sg.m2a.models.Game;
import com.sg.m2a.models.Round;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class RoundDaoTest {

    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    static Game g1;
    static Round r1;
    static Round r1update;
    static Round r2;

    public RoundDaoTest() {
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
        final String FIRST_GUESS = "2345"; //fail, 3 partials
        final String FIRST_GUESS_UPDATE = "1111"; //fail, 1 exact
        final String SECOND_GUESS = "1234"; //correct

        //game obj
        g1 = new Game();
        g1.setAnswer(SECOND_GUESS);
        g1.setIsFinished(false);
        List<Round> gameRounds = new ArrayList<>();
        g1.setRounds(gameRounds); //empty

        /*add the only game*/
        Game game1 = gameDao.createGame(g1);

        //round obj's
        r1 = new Round();
        r1.setGameId(game1.getGameId());
        r1.setGuess(FIRST_GUESS);
        r1.setDigitMatches("e:0:p:3");

        r1update = new Round();
        //must retrieve roundId from creation of r1 in method
        r1update.setGameId(game1.getGameId());
        r1update.setGuess(FIRST_GUESS_UPDATE);
        r1update.setDigitMatches("e:1:p:0");

        r2 = new Round();
        r2.setGameId(game1.getGameId());
        r2.setGuess(SECOND_GUESS);
        r2.setDigitMatches("e:4:p:0");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createRound and readRoundById method, of class RoundDao.
     */
    @Test
    public void testCreateReadRound() {
        //arrange
        Round r = roundDao.createRound(r1);

        //act
        Round fromDao = roundDao.readRoundById(r1.getRoundId());

        //assert
        assertEquals(r, fromDao);
    }

    /**
     * Test of updateRound method, of class RoundDao.
     */
    @Test
    public void testUpdateRound() {
        //arrange
        Round r = roundDao.createRound(r1);
        Round original = roundDao.readRoundById(r.getRoundId());

        r1update.setRoundId(r.getRoundId());

        //act
        boolean wasUpdated = roundDao.updateRound(r1update);
        Round updated = roundDao.readRoundById(r.getRoundId());

        //assert
        assertTrue(wasUpdated);
        assertNotEquals(original, updated);
        assertEquals(original.getRoundId(), updated.getRoundId());
    }

    /**
     * Test of deleteRoundByID method, of class RoundDao.
     */
    @Test
    public void testDeleteRoundByID() {
        //arrange
        Round round1 = roundDao.createRound(r1);
        Round round2 = roundDao.createRound(r2);

        //act
        roundDao.deleteRoundByID(round1.getRoundId());
        Round r1FromDao = roundDao.readRoundById(round1.getRoundId());
        Round r2FromDao = roundDao.readRoundById(round2.getRoundId());

        //assert
        assertNull(r1FromDao);
        assertNotNull(r2FromDao);
    }

}
