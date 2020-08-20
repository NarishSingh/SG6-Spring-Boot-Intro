package com.sg.m2a.models;

import java.util.List;
import java.util.Objects;

public class Game {

    private int gameId;
    private String answer;
    private boolean isFinished;
    private List<Round> rounds;

    /*ctors*/
    public Game() {
    }

    /*get/set*/
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }
    
    /*testing*/
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.gameId;
        hash = 53 * hash + Objects.hashCode(this.answer);
        hash = 53 * hash + (this.isFinished ? 1 : 0);
        hash = 53 * hash + Objects.hashCode(this.rounds);
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
        final Game other = (Game) obj;
        if (this.gameId != other.gameId) {
            return false;
        }
        if (this.isFinished != other.isFinished) {
            return false;
        }
        if (!Objects.equals(this.answer, other.answer)) {
            return false;
        }
        if (!Objects.equals(this.rounds, other.rounds)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Game{" + "gameId=" + gameId + ", answer=" + answer + ", isFinished=" 
                + isFinished + ", rounds=" + rounds + '}';
    }
    
}
