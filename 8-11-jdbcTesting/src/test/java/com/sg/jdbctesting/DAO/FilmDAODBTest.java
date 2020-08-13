package com.sg.jdbctesting.DAO;

import com.sg.jdbctesting.DTO.Actor;
import com.sg.jdbctesting.DTO.Film;
import com.sg.jdbctesting.app.TestApplicationConfiguration;
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

/**
 *
 * @author naris
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class FilmDAODBTest {
    
    @Autowired
    ActorDAO actorDAO;
    
    @Autowired
    FilmDAO filmDAO;
    
    public FilmDAODBTest() {
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
     * Test of createFilm and readFilmById method, of class FilmDAODB.
     */
    @Test
    public void testCreateReadFilm() {
        //arrange
        Film f1 = new Film();
        f1.setTitle("A Movie");
        f1.setDescription("Movie description");
        f1.setReleaseYr(2020);
        f1.setLastUpdate(LocalDateTime.now().withNano(0));
        
        Actor a1 = new Actor();
        a1.setFirstName("Narish");
        a1.setLastName("Singh");
        a1 = actorDAO.createActor(a1);
        
        List<Actor> f1Actors = new ArrayList<>();
        f1Actors.add(a1);
        f1.setFilmActors(f1Actors);
        
        //act
        Film addedFilm = filmDAO.createFilm(f1);
        Film fromDao = filmDAO.readFilmById(addedFilm.getFilmId());
        
        //assert
        assertEquals(addedFilm, fromDao);
    }

    /**
     * Test of readAllFilms method, of class FilmDAODB.
     */
    @Test
    public void testReadAllFilms() {
        //arrange
        Film f1 = new Film();
        f1.setTitle("A Movie");
        f1.setDescription("Movie description");
        f1.setReleaseYr(2020);
        f1.setLastUpdate(LocalDateTime.now().withNano(0));
        
        Actor a1 = new Actor();
        a1.setFirstName("Narish");
        a1.setLastName("Singh");
        a1 = actorDAO.createActor(a1);
        
        List<Actor> f1Actors = new ArrayList<>();
        f1Actors.add(a1);
        f1.setFilmActors(f1Actors);
        
        Film f2 = new Film();
        f2.setTitle("A Movie - Part II");
        f2.setDescription("Movie description 2");
        f2.setReleaseYr(2020);
        f2.setLastUpdate(LocalDateTime.now().withNano(0));
        
        Actor a2 = new Actor();
        a2.setFirstName("N.");
        a2.setLastName("Singh");
        a2 = actorDAO.createActor(a2);
        
        List<Actor> f2Actors = new ArrayList<>();
        f2Actors.add(a2);
        f2.setFilmActors(f2Actors);
        
        //act
        Film firstAdded = filmDAO.createFilm(f1);
        Film secondAdded = filmDAO.createFilm(f2);
        
        List<Film> allFilms = filmDAO.readAllFilms();
        
        //assert
        assertEquals(allFilms.size(), 2);
        assertTrue("DB should contain A Movie", allFilms.contains(firstAdded));
        assertTrue("DB should contain A Movie = Part II", allFilms.contains(secondAdded));
    }


    /**
     * Test of updateFilm method, of class FilmDAODB.
     */
    @Test
    public void testUpdateFilm() {
        //arrange
        //original
        Film f1 = new Film();
        f1.setTitle("A Movie");
        f1.setDescription("Movie description");
        f1.setReleaseYr(2020);
        f1.setLastUpdate(LocalDateTime.now().withNano(0));
        
        Actor a1 = new Actor();
        a1.setFirstName("Narish");
        a1.setLastName("Singh");
        a1 = actorDAO.createActor(a1);
        
        List<Actor> f1Actors = new ArrayList<>();
        f1Actors.add(a1);
        f1.setFilmActors(f1Actors);
        
        Film fromDao = filmDAO.createFilm(f1);
        
        assertEquals(f1, fromDao);
        
        //edit - add an actor
        Actor a2 = new Actor();
        a2.setFirstName("N.");
        a2.setLastName("Singh");
        a2 = actorDAO.createActor(a2);
        
        f1Actors.add(a2);
        f1.setFilmActors(f1Actors);
        
        //act
        filmDAO.updateFilm(f1);
        
        //assert
        assertNotEquals(f1, fromDao);
        
        Film editFromDao = filmDAO.readFilmById(f1.getFilmId());
        assertEquals(f1, editFromDao);
    }

    /**
     * Test of deleteFilm method, of class FilmDAODB.
     */
    @Test
    public void testDeleteFilm() {
        //arrange
        Film f1 = new Film();
        f1.setTitle("A Movie");
        f1.setDescription("Movie description");
        f1.setReleaseYr(2020);
        f1.setLastUpdate(LocalDateTime.now().withNano(0));
        
        Actor a1 = new Actor();
        a1.setFirstName("Narish");
        a1.setLastName("Singh");
        a1 = actorDAO.createActor(a1);
        
        List<Actor> f1Actors = new ArrayList<>();
        f1Actors.add(a1);
        f1.setFilmActors(f1Actors);
        
        //act
        Film addedFilm = filmDAO.createFilm(f1);
        
        filmDAO.deleteFilm(addedFilm);
        
        Film fromDao = filmDAO.readFilmById(addedFilm.getFilmId());
        
        //assert
        assertNull(fromDao);
    }

    /**
     * Test of getFilmsByActors method, of class FilmDAODB.
     */
    @Test
    public void testGetFilmsByActors() {
        //arrange
        Actor a1 = new Actor();
        a1.setFirstName("Narish");
        a1.setLastName("Singh");
        a1 = actorDAO.createActor(a1);
        
        //film 1
        Film f1 = new Film();
        f1.setTitle("A Movie");
        f1.setDescription("Movie description");
        f1.setReleaseYr(2020);
        f1.setLastUpdate(LocalDateTime.now().withNano(0));
        
        List<Actor> f1Actors = new ArrayList<>();
        f1Actors.add(a1);
        f1.setFilmActors(f1Actors);
        
        //film 2
        Film f2 = new Film();
        f2.setTitle("A Movie - Part II");
        f2.setDescription("Movie description 2");
        f2.setReleaseYr(2020);
        f2.setLastUpdate(LocalDateTime.now().withNano(0));
        
        List<Actor> f2Actors = new ArrayList<>();
        f2Actors.add(a1);
        f2.setFilmActors(f2Actors);
        
        //film 3 - with unrelated actor
        Film f3 = new Film();
        f3.setTitle("A Movie - Part III");
        f3.setDescription("Movie description 3");
        f3.setReleaseYr(2020);
        f3.setLastUpdate(LocalDateTime.now().withNano(0));
        
        Actor a2 = new Actor();
        a2.setFirstName("N.");
        a2.setLastName("Singh");
        a2 = actorDAO.createActor(a2);
        
        List<Actor> f3Actors = new ArrayList<>();
        f3Actors.add(a2);
        f3.setFilmActors(f3Actors);
        
        //act
        List<Film> a1Films = filmDAO.getFilmsByActors(a1);
        
        //assert
        assertEquals(a1Films.size(), 2);
        assertTrue(a1Films.contains(f1));
        assertTrue(a1Films.contains(f2));
        assertFalse(a1Films.contains(f3));
    }
    
}
