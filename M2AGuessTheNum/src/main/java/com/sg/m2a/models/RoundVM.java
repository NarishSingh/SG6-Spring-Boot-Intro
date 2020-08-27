/*
remapping data into a viewable format for api's consumer
important for data hiding + beautification
 */
package com.sg.m2a.models;

import java.util.Objects;

/**
 *
 * @author naris
 */
public class RoundVM {

    private String guess;
    private String roundResult;

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public String getRoundResult() {
        return roundResult;
    }

    public void setRoundResult(String roundResult) {
        this.roundResult = roundResult;
    }

    /*testing*/
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.guess);
        hash = 53 * hash + Objects.hashCode(this.roundResult);
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
        if (!Objects.equals(this.guess, other.guess)) {
            return false;
        }
        if (!Objects.equals(this.roundResult, other.roundResult)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RoundVM{" + "guess=" + guess + ", roundResult=" + roundResult + '}';
    }

}
