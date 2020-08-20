package com.sg.m2a.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Round {

    private int roundId;
    private int gameId; //fk to Game table
    private String guess;
    private LocalDateTime time;
    private String digitMatches;

    /*ctors*/
    public Round() {
    }

    /*get/set*/
    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
    
    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getDigitMatches() {
        return digitMatches;
    }

    public void setDigitMatches(String digitMatches) {
        this.digitMatches = digitMatches;
    }
    
    /*testing*/
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.roundId;
        hash = 53 * hash + this.gameId;
        hash = 53 * hash + Objects.hashCode(this.guess);
        hash = 53 * hash + Objects.hashCode(this.time);
        hash = 53 * hash + Objects.hashCode(this.digitMatches);
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
        final Round other = (Round) obj;
        if (this.roundId != other.roundId) {
            return false;
        }
        if (this.gameId != other.gameId) {
            return false;
        }
        if (!Objects.equals(this.guess, other.guess)) {
            return false;
        }
        if (!Objects.equals(this.digitMatches, other.digitMatches)) {
            return false;
        }
        if (!Objects.equals(this.time, other.time)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Round{" + "roundId=" + roundId + ", gameId=" + gameId + ", guess=" 
                + guess + ", time=" + time + ", digitMatches=" + digitMatches + '}';
    }
    
}
