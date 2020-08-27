package com.sg.m2a.service;

import com.sg.m2a.TestApplicationConfiguration;
import com.sg.m2a.data.GameDao;
import com.sg.m2a.data.RoundDao;
import com.sg.m2a.models.Game;
import com.sg.m2a.models.Round;
import com.sg.m2a.models.RoundVM;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GuessServiceImplTest {

    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;

    @Autowired
    private GuessService testServ;

    public GuessServiceImplTest() {
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
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of newGame method, of class GuessServiceImpl.
     */
    @Test
    public void testNewGame() {
        //arrange
        Game newTest = testServ.newGame();

        //act
        try {
            String newTestAnswer = testServ.validateDigitSet(newTest.getAnswer());
        } catch (DuplicateDigitEntryException e) {
            fail("Answer should have 4 unique digits");
        }

        //assert
        assertNotNull(newTest);
    }

    /**
     * Test of guess method, of class GuessServiceImpl - Pass with a valid
     * guess, may or may not win
     */
    @Test
    public void testGuessDigitPass() throws Exception {
        //arrange
        final String testGuess = "1234";
        Game newTest = testServ.newGame();
        Round testRound;

        //act
        try {
            testRound = testServ.guess(testGuess, newTest.getGameId());

            //assert
            assertNotNull(testRound);
        } catch (DuplicateDigitEntryException
                | NotFoundException e) {
            fail("Valid game and guess");
        }
    }

    /**
     * Test of guess method, of class GuessServiceImpl - Pass with a game win
     */
    @Test
    public void testGuessGameWin() throws Exception {
        //arrange
        Game newTest = testServ.newGame();

        final String exactGuess = newTest.getAnswer();
        final String perfectResult = "e:4:p:0";
        Round testRound;

        assertFalse(newTest.isIsFinished());

        //act and assert
        try {
            testRound = testServ.guess(exactGuess, newTest.getGameId());

            assertNotNull(testRound);
            assertEquals(testRound.getDigitMatches(), perfectResult);

            Game afterRound = testServ.readGame(newTest.getGameId());
            assertTrue(afterRound.isIsFinished());
        } catch (DuplicateDigitEntryException
                | NotFoundException e) {
            fail("Valid game and guess");
        }
    }

    /**
     * Test of guess method, of class GuessServiceImpl - Fail by duplicate
     * digits
     */
    @Test
    public void testGuessDigitFail() throws Exception {
        //arrange
        final String testGuess = "9999";
        Game newTest = testServ.newGame();
        Round testRound;

        //act assert
        try {
            testRound = testServ.guess(testGuess, newTest.getGameId());
            fail();
        } catch (NotFoundException e) {
            fail("Valid game");
        } catch (DuplicateDigitEntryException e) {
            return;
        }
    }

    /**
     * Test of guess method, of class GuessServiceImpl - Fail by invalid gameId
     */
    @Test
    public void testGuessGameReadFail() throws Exception {
        //arrange
        final int badGameId = 99999999;
        final String testGuess = "1234";

        //act and assert
        try {
            Round testRound = testServ.guess(testGuess, badGameId);
            fail();
        } catch (NotFoundException e) {
            return;
        } catch (DuplicateDigitEntryException e) {
            fail("Valid guess");
        }
    }

    /**
     * Test of readAllGames method, of class GuessServiceImpl.
     */
    @Test
    public void testReadAllGames() {
        //arrange
        //incomplete game - one round unlikely
        Game game1 = testServ.newGame();
        try {
            Round g1r1 = testServ.guess("5678", game1.getGameId());
        } catch (DuplicateDigitEntryException
                | NotFoundException e) {
            fail("Valid game and guess");
        }

        //complete game - one round win
        Game game2 = testServ.newGame();
        try {
            Round g2r1 = testServ.guess(game2.getAnswer(), game2.getGameId());
        } catch (DuplicateDigitEntryException
                | NotFoundException e) {
            fail("Valid game and guess");
        }

        //act
        List<Game> allGames = testServ.readAllGames();
        Game g1FromDb = allGames.get(0);
        Game g2FromDb = allGames.get(1);

        //assert
        //exists and was updated
        assertFalse(allGames.contains(game1));
        assertEquals(game1.getGameId(), g1FromDb.getGameId());
        assertNotEquals(game1, g1FromDb);
        assertFalse(allGames.contains(game2));
        assertEquals(game2.getGameId(), g2FromDb.getGameId());
        assertNotEquals(game2, g2FromDb);

        //status check
        assertFalse(g1FromDb.isIsFinished());
        assertTrue(g2FromDb.isIsFinished());

        //data hiding
        assertTrue(g1FromDb.getAnswer().equals("****"));
        assertFalse(g2FromDb.getAnswer().equals("****"));
    }

    /**
     * Test of readGame method, of class GuessServiceImpl.
     */
    @Test
    public void testReadGame() throws Exception {
        //arrange
        Game game1 = testServ.newGame();

        //act & assert
        Game fromDbBeforeWin = testServ.readGame(game1.getGameId());
        //incomplete - must hide answer
        assertNotNull(fromDbBeforeWin);
        assertNotEquals(game1, fromDbBeforeWin);
        assertTrue(fromDbBeforeWin.getAnswer().equals("****"));

        try {
            Round win = testServ.guess(game1.getAnswer(), game1.getGameId());
            Game fromDbWin = testServ.readGame(game1.getGameId());

            //complete - must show answer
            assertNotNull(fromDbWin);
            assertNotEquals(game1, fromDbWin);
            assertFalse(fromDbWin.getAnswer().equals("****"));
        } catch (DuplicateDigitEntryException
                | NotFoundException e) {
            fail("Valid game and guess");
        }
    }

    /**
     * Test of readGameRounds method, of class GuessServiceImpl.
     */
    @Test
    public void testReadGameRounds() throws Exception {
        //arrange
        Game g1 = testServ.newGame();

        //act & assert
        try {
            Round r1 = testServ.guess("1234", g1.getGameId());
            Round r2 = testServ.guess("2345", g1.getGameId());
            Round r3 = testServ.guess("3456", g1.getGameId());

            assertNotNull(r1);
            assertNotNull(r2);
            assertNotNull(r3);

            List<Round> g1rounds = testServ.readGameRounds(g1.getGameId());

            assertEquals(g1rounds.size(), 3);
            assertTrue(g1rounds.contains(r1));
            assertTrue(g1rounds.contains(r2));
            assertTrue(g1rounds.contains(r3));
        } catch (DuplicateDigitEntryException
                | NotFoundException e) {
            fail("Valid game and guess");
        }
    }

    /**
     * Test of getAllRoundVM method, of class GuessServiceImpl.
     */
    @Test
    public void testGetAllRoundVM() {
        //arrange
        Game g1 = testServ.newGame();
        Game g2 = testServ.newGame();

        //act & assert
        try {
            Round r1 = testServ.guess("1234", g1.getGameId());
            Round r2 = testServ.guess("2345", g1.getGameId());
            Round r3 = testServ.guess("3456", g1.getGameId());
            Round r4 = testServ.guess("1234", g2.getGameId());
            Round r5 = testServ.guess("2345", g2.getGameId());

            assertNotNull(r1);
            assertNotNull(r2);
            assertNotNull(r3);
            assertNotNull(r4);
            assertNotNull(r5);

            List<RoundVM> allVMrounds = testServ.getAllRoundVM();

            assertEquals(allVMrounds.size(), 5);
        } catch (DuplicateDigitEntryException
                | NotFoundException e) {
            fail("Valid game and guess");
        }
    }

}
