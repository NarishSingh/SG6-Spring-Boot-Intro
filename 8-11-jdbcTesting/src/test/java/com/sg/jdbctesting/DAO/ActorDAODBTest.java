package com.sg.jdbctesting.DAO;

import com.sg.jdbctesting.DTO.Actor;
import com.sg.jdbctesting.DTO.Film;
import com.sg.jdbctesting.app.TestApplicationConfiguration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author naris
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class ActorDAODBTest {
    
    @Autowired
    ActorDAO actorDAO;
    
    @Autowired
    FilmDAO filmDAO;
    
    public ActorDAODBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Actor> actors = actorDAO.readAllActors();
        for (Actor actor : actors) {
            actorDAO.deleteActor(actor);
        }
        
        List<Film> films = filmDAO.readAllFilms();
        for (Film film : films) {
            filmDAO.deleteFilm(film);
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createActor method, of class ActorDAODB.
     */
    @Test
    public void testCreateReadByIdActor() {
        //arrange
        Actor a1 = new Actor();
        a1.setFirstName("Narish");
        a1.setLastName("Singh");
        a1.setLastUpdate(LocalDateTime.now());
        
        //act
        Actor actor = actorDAO.createActor(a1);
        
        Actor addedActor = actorDAO.readActorById(a1.getActorId());
        
        //assert
        assertEquals(actor, addedActor);
    }

    /**
     * Test of readAllActors method, of class ActorDAODB.
     */
    @Test
    public void testReadAllActors() {
        //arrange
        Actor a1 = new Actor();
        a1.setFirstName("Narish");
        a1.setLastName("Singh");
        a1.setLastUpdate(LocalDateTime.now());
        
        Actor a2 = new Actor();
        a2.setFirstName("Singh");
        a2.setLastName("Singh");
        a2.setLastUpdate(LocalDateTime.now());
        
        Actor addedActor1 = actorDAO.createActor(a1);
        Actor addedActor2 = actorDAO.createActor(a2);
        
        //act
        List<Actor> actors = actorDAO.readAllActors();
        
        //assert
        assertEquals(2, actors.size());
        assertTrue(actors.contains(a1));
        assertTrue(actors.contains(a2));
    }

    /**
     * Test of deleteActor method, of class ActorDAODB.
     */
    @Test
    public void testDeleteActor() {
        //arrange
        Actor a1 = new Actor();
        a1.setFirstName("Narish");
        a1.setLastName("Singh");
        a1.setLastUpdate(LocalDateTime.now());
        
        Actor a2 = new Actor();
        a2.setFirstName("Singh");
        a2.setLastName("Singh");
        a2.setLastUpdate(LocalDateTime.now());
        
        Actor addedActor1 = actorDAO.createActor(a1);
        Actor addedActor2 = actorDAO.createActor(a2);
        
        //act
        Actor removedActor = actorDAO.deleteActor(a2);
        
        List<Actor> actors = actorDAO.readAllActors();
        
        //assert
        assertEquals(1, actors.size());
        assertTrue(actors.contains(a1));
        assertFalse(actors.contains(a2));
    }

    /**
     * Test of getActorsForFilm method, of class ActorDAODB.
     */
    @Test
    public void testGetActorsByFilm() {
        //arrange
        Actor a1 = new Actor();
        a1.setFirstName("Narish");
        a1.setLastName("Singh");
        a1.setLastUpdate(LocalDateTime.now());
        
        Actor a2 = new Actor();
        a2.setFirstName("Singh");
        a2.setLastName("Singh");
        a2.setLastUpdate(LocalDateTime.now());
        
        Actor a3 = new Actor();
        a3.setFirstName("N");
        a3.setLastName("Singh");
        a3.setLastUpdate(LocalDateTime.now());
        
        Film f1 = new Film();
        f1.setTitle("The Narish Movie");
        f1.setReleaseYr(1995);
        f1.setLastUpdate(LocalDateTime.now());
        f1.setDescription("A film about nothing");
        List<Actor> narishMovieActors = new ArrayList<>();
        narishMovieActors.add(a1);
        narishMovieActors.add(a2);
        narishMovieActors.add(a3);
        f1.setFilmActors(narishMovieActors);
        
        //act
        List<Actor> actorsInFilm = actorDAO.getActorsByFilm(f1);
        
       //assert
       assertEquals(3, actorsInFilm.size());
       assertTrue(actorsInFilm.contains(a1));
       assertTrue(actorsInFilm.contains(a2));
       assertTrue(actorsInFilm.contains(a3));
    }
    
}
