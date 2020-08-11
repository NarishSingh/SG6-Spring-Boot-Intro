package com.sg.jdbctesting.DAO;

import com.sg.jdbctesting.DTO.Actor;
import com.sg.jdbctesting.DTO.Film;
import java.util.List;

/**
 *
 * @author naris
 */
public interface FilmDAO {
    public Film createFilm(Film film);
    
    public List<Film> readAllFilms();
    
    public Film readFilmById(int filmId);
    
    public Film updateFilm(Film film);
    
    public Film deleteFilm(Film film);
    
    public List<Film> getFilmsByActors(Actor actor);
}
