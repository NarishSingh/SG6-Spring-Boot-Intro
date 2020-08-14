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

        for (Film film : filmList) {
            film.setFilmActors(getActorsForFilm(film));
        }
        
        return filmList;
    }

    @Override
    public Film readFilmById(int filmId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Film updateFilm(Film film) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Film deleteFilm(Film film) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Film> getFilmsByActors(Actor actor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
