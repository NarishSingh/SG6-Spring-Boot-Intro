package com.sg.jdbctesting.DTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author naris
 */
public class Film {

    private int filmId;
    private String title;
    private String description;
    private int releaseYr;
    private LocalDateTime lastUpdate;
    private List<Actor> filmActors;

    /*ctor*/
    public Film() {
    }

    public Film(int filmId, String title, String description, int releaseYr, LocalDateTime lastUpdate) {
        this.filmId = filmId;
        this.title = title;
        this.description = description;
        this.releaseYr = releaseYr;
        this.lastUpdate = lastUpdate;
    }

    /*getter/setter*/
    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleaseYr() {
        return releaseYr;
    }

    public void setReleaseYr(int releaseYr) {
        this.releaseYr = releaseYr;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<Actor> getFilmActors() {
        return filmActors;
    }

    public void setFilmActors(List<Actor> filmActors) {
        this.filmActors = filmActors;
    }

    /*test*/
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.filmId;
        hash = 67 * hash + Objects.hashCode(this.title);
        hash = 67 * hash + Objects.hashCode(this.description);
        hash = 67 * hash + this.releaseYr;
        hash = 67 * hash + Objects.hashCode(this.lastUpdate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Film other = (Film) obj;
        if (this.filmId != other.filmId) {
            return false;
        }
        if (this.releaseYr != other.releaseYr) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.lastUpdate, other.lastUpdate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Film{" + "filmId=" + filmId + ", title=" + title + ", description=" + description + ", releaseYr=" + releaseYr + ", lastUpdate=" + lastUpdate + '}';
    }
}
