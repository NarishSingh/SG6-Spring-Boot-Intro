package com.sg.jdbctesting.DAO;

import com.sg.jdbctesting.DTO.Actor;
import com.sg.jdbctesting.DTO.Film;
import java.util.List;

/**
 *
 * @author naris
 */
public interface ActorDAO {

    public Actor createActor(Actor newActor);

    public List<Actor> readAllActors();

    public Actor readActorById(int actorId);

    public Actor deleteActor(Actor removedActor);

    public List<Actor> getActorsByFilm(Film film);
}
