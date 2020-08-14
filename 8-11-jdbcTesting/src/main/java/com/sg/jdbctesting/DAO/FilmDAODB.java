package com.sg.jdbctesting.DAO;

import com.sg.jdbctesting.DAO.ActorDAODB.ActorMapper;
import com.sg.jdbctesting.DTO.Actor;
import com.sg.jdbctesting.DTO.Film;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FilmDAODB implements FilmDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Film createFilm(Film film) {
        //insert into film
        String insertFilmQuery = "INSERT INTO film(title, description, release_year, last_update) "
                + "VALUES(?,?,?,?)";
        jdbc.update(insertFilmQuery,
                film.getTitle(),
                film.getDescription(),
                film.getReleaseYr(),
                Timestamp.valueOf(LocalDateTime.now().withNano(0))
        );

        //grab ai key from insert
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        film.setFilmId(newId);

        //insert into film_actor
        associateFilmActor(film);

        return film;
    }

    @Override
    public List<Film> readAllFilms() {
        String readAllQuery = "SELECT * FROM film";
        List<Film> filmList = jdbc.query(readAllQuery, new FilmMapper());

        //FilmMapper doesn't handle Lists, need to setup that field seperately
        for (Film film : filmList) {
            film.setFilmActors(getActorsForFilm(film));
        }
        
        return filmList;
    }

    @Override
    public Film readFilmById(int filmId) {
        try {
            String selectFilmById = "SELECT * FROM film "
                    + "WHERE film_id = ?";
            Film film = jdbc.queryForObject(selectFilmById, 
                    new FilmMapper(), 
                    filmId);
            
            film.setFilmActors(getActorsForFilm(film)); //pull in list of actors for film
            
            return film;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public Film updateFilm(Film film) {
        String updateQuery = "UPDATE film "
                + "SET title = ?, "
                    + "description = ?, "
                    + "release_year = ?, "
                    + "last_update = ?, "
                + "WHERE film_id = ?";
        int filmUpdate = jdbc.update(updateQuery, 
                film.getTitle(),
                film.getDescription(),
                film.getReleaseYr(),
                Timestamp.valueOf(film.getLastUpdate())
        );
        
        //clear and update film_actor
        String clearFilmActorQuery = "DELETE FROM film_actor "
                + "WHERE film_id = ?";
        jdbc.update(clearFilmActorQuery, film.getFilmId());
        
        associateFilmActor(film);
        
        //.update returns int so if only 1 row affected the update was successful
        if (filmUpdate == 1) {
            return film;
        } else {
            return null;
        }
    }

    @Override
    public Film deleteFilm(Film film) {
        //delete from bridge table first
        String deleteFilmActorQuery = "DELETE FROM film_actor "
                + "WHERE film_id = ?";
        jdbc.update(deleteFilmActorQuery, film.getFilmId());
        
        //delete film
        String deleteFilmQuery = "DELETE FROM film "
                + "WHERE film_id = ?";
        int filmDeletion = jdbc.update(deleteFilmQuery, film.getFilmId());
        
        if (filmDeletion == 1) {
            return film;
        } else {
            return null;
        }
    }

    @Override
    public List<Film> getFilmsByActors(Actor actor) {
        String selectFilmsByActorQuery = "SELECT * FROM film_actor "
                + "WHERE actor_id = ?";
        List<Film> filmsForActor = jdbc.query(selectFilmsByActorQuery, 
                new FilmMapper(), 
                actor.getActorId()
        );
        
        associateActorsToFilms(filmsForActor);
        
        return filmsForActor;
    }

    /*helper methods*/
    private void associateFilmActor(Film film) {
        //insert into film_actor
        String insertFilmActorQuery = "INSERT INTO film_actor(film_id, actor_id) "
                + "VALUES(?,?)";
        for (Actor filmActor : film.getFilmActors()) {
            jdbc.update(insertFilmActorQuery,
                    film.getFilmId(),
                    filmActor.getActorId()
            );
        }
    }

    private List<Actor> getActorsForFilm(Film film) {
        String selectActorsForFilm = "SELECT a.* FROM Actor a "
                + "JOIN film_actor fa ON fa.actor_id = a.actor_id "
                + "WHERE fa.film_id = ?";
        return jdbc.query(selectActorsForFilm,
                new ActorMapper(),
                film.getFilmId()
        );
    }
    
    private void associateActorsToFilms(List<Film> films){
        for (Film film : films) {
            film.setFilmActors(getActorsForFilm(film));
        }
    }

    /*rowmapper*/
    public static final class FilmMapper implements RowMapper<Film> {

        @Override
        public Film mapRow(ResultSet rs, int index) throws SQLException {
            Film f = new Film();
            f.setFilmId(rs.getInt("film_id"));
            f.setTitle(rs.getString("title"));
            f.setDescription(rs.getString("description"));
            f.setReleaseYr(rs.getInt("release_year"));
            f.setLastUpdate(rs.getTimestamp("last_update").toLocalDateTime());

            return f;
        }
    }
}
