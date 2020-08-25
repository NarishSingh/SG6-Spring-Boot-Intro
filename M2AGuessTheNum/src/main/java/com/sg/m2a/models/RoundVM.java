/*
remapping data into a viewable format for api's consumer
important for data hiding + beautification
 */
package com.sg.m2a.models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author naris
 */
public class RoundVM {

    private int roundId;
    private int gameId; //fk to Game table
    private String guess;
    private LocalDateTime time;
    private int exactMatches;
    private int partialMatches;
    private String digitMatches;

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

    public int getExactMatches() {
        return exactMatches;
    }

    public void setExactMatches(int exactMatches) {
        this.exactMatches = exactMatches;
    }

    public int getPartialMatches() {
        return partialMatches;
    }

    public void setPartialMatches(int partialMatches) {
        this.partialMatches = partialMatches;
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
        int hash = 5;
        hash = 29 * hash + this.roundId;
        hash = 29 * hash + this.gameId;
        hash = 29 * hash + Objects.hashCode(this.guess);
        hash = 29 * hash + Objects.hashCode(this.time);
        hash = 29 * hash + this.exactMatches;
        hash = 29 * hash + this.partialMatches;
        hash = 29 * hash + Objects.hashCode(this.digitMatches);
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
        final RoundVM other = (RoundVM) obj;
        if (this.roundId != other.roundId) {
            return false;
        }
        if (this.gameId != other.gameId) {
            return false;
        }
        if (this.exactMatches != other.exactMatches) {
            return false;
        }
        if (this.partialMatches != other.partialMatches) {
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
        return "RoundVM{" + "roundId=" + roundId + ", gameId=" + gameId + ", guess=" 
                + guess + ", time=" + time + ", exactMatches=" + exactMatches 
                + ", partialMatches=" + partialMatches + ", digitMatches=" 
                + digitMatches + '}';
    }
    
}
