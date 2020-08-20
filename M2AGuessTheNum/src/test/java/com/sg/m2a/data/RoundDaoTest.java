package com.sg.m2a.data;

import com.sg.m2a.TestApplicationConfiguration;
import com.sg.m2a.models.Game;
import com.sg.m2a.models.Round;
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
public class RoundDaoTest {
    
    @Autowired
    GameDao gameDao;
    
    @Autowired
    RoundDao roundDao;
    
    public RoundDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        //test obj's
        //TODO set up obj's
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
     * Test of createRound method, of class RoundDao.
     */
    @Test
    public void testCreateRound() {
    }

    /**
     * Test of readRoundById method, of class RoundDao.
     */
    @Test
    public void testReadRoundById() {
    }

    /**
     * Test of updateRound method, of class RoundDao.
     */
    @Test
    public void testUpdateRound() {
    }

    /**
     * Test of deleteRoundByID method, of class RoundDao.
     */
    @Test
    public void testDeleteRoundByID() {
    }

}
