package com.sg.m2a.service;

import com.sg.m2a.data.GameDao;
import com.sg.m2a.data.RoundDao;
import org.junit.*;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;

public class GuessServiceImplTest {
    
    @Autowired
    RoundDao roundDao;

    @Autowired
    GameDao gameDao;
    
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
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of newGame method, of class GuessServiceImpl.
     */
    @Test
    public void testNewGame() {
    }

    /**
     * Test of guess method, of class GuessServiceImpl.
     */
    @Test
    public void testGuess() throws Exception {
    }

    /**
     * Test of readAllGames method, of class GuessServiceImpl.
     */
    @Test
    public void testReadAllGames() {
    }

    /**
     * Test of readGame method, of class GuessServiceImpl.
     */
    @Test
    public void testReadGame() throws Exception {
    }

    /**
     * Test of readGameRounds method, of class GuessServiceImpl.
     */
    @Test
    public void testReadGameRounds() throws Exception {
    }

    /**
     * Test of validateGuess method, of class GuessServiceImpl.
     */
    @Test
    public void testValidateGuess() throws Exception {
    }

    /**
     * Test of screenInProgressGame method, of class GuessServiceImpl.
     */
    @Test
    public void testScreenInProgressGame() {
    }

    /**
     * Test of convert method, of class GuessServiceImpl.
     */
    @Test
    public void testConvert() {
    }

    /**
     * Test of getAllRoundVM method, of class GuessServiceImpl.
     */
    @Test
    public void testGetAllRoundVM() {
    }
    
}
