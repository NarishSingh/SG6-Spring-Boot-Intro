package com.sg.m2a.service;

import com.sg.m2a.TestApplicationConfiguration;
import com.sg.m2a.data.GameDao;
import com.sg.m2a.data.RoundDao;
import com.sg.m2a.models.Game;
import com.sg.m2a.models.Round;
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

        //act
        //assert
    }

    /**
     * Test of readGame method, of class GuessServiceImpl.
     */
    @Test
    public void testReadGame() throws Exception {
        //arrange

        //act
        //assert
    }

    /**
     * Test of readGameRounds method, of class GuessServiceImpl.
     */
    @Test
    public void testReadGameRounds() throws Exception {
        //arrange

        //act
        //assert
    }

    /**
     * Test of validateDigitSet method, of class GuessServiceImpl.
     */
    @Test
    public void testValidateDigitSet() throws Exception {
        //arrange

        //act
        //assert
    }

    /**
     * Test of screenInProgressGame method, of class GuessServiceImpl.
     */
    @Test
    public void testScreenInProgressGame() {
        //arrange

        //act
        //assert
    }

    /**
     * Test of convert method, of class GuessServiceImpl.
     */
    @Test
    public void testConvert() {
        //arrange

        //act
        //assert
    }

    /**
     * Test of getAllRoundVM method, of class GuessServiceImpl.
     */
    @Test
    public void testGetAllRoundVM() {
        //arrange

        //act
        //assert
    }

}
