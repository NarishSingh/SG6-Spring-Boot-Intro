package com.sg.jdbctesting.DAO;

import com.sg.jdbctesting.DTO.Actor;
import com.sg.jdbctesting.DTO.Film;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ActorDAODB implements ActorDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Actor createActor(Actor newActor) {
        //insert to db
        String createQuery = "INSERT INTO actor(first_name, last_name, last_update)"
                + "VALUES(?,?,?)";
        jdbc.update(createQuery,
                newActor.getFirstName(),
                newActor.getLastName(),
                newActor.getLastUpdate());

        //grab auto++ key and set
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        newActor.setActorId(newId);

        return newActor;
    }

    @Override
    public List<Actor> readAllActors() {
        String readAllQuery = "SELECT * FROM actor";
        return jdbc.query(readAllQuery, new ActorMapper());
    }

    @Override
    public Actor readActorById(int actorId) {
        try {
            String readQuery = "SELECT * FROM actor"
                    + "WHERE actor_id = ?";
            return jdbc.queryForObject(readQuery, new ActorMapper(), actorId);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Actor deleteActor(Actor removedActor) {
        String deleteQuery = "DELETE FROM actor"
                + "WHERE actor_id = ?";
        int rowsDeleted = jdbc.update(deleteQuery, removedActor.getActorId());

        if (rowsDeleted == 1) {
            return removedActor;
        } else {
            return null;
        }
    }

    @Override
    public List<Actor> getActorsByFilm(Film film) {
        String actorListQuery = "SELECT a.* FROM actor a"
                + "INNER JOIN film_actor fa ON a.actor_id = fa.actor_id "
                + "WHERE fa.film_id = ?";

        List<Actor> actorsByFilm = jdbc.query(actorListQuery, new ActorMapper(), film.getTitle());

        return actorsByFilm;
    }

    public static final class ActorMapper implements RowMapper<Actor> {

        @Override
        public Actor mapRow(ResultSet rs, int index) throws SQLException {
            Actor a = new Actor();
            a.setActorId(rs.getInt("actor_id"));
            a.setFirstName(rs.getString("first_name"));
            a.setLastName(rs.getString("last_name"));
            a.setLastUpdate(rs.getTimestamp("last_update").toLocalDateTime());

            return a;
        }
    }

}
