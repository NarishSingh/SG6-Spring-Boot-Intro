/*
remapping data into a viewable format for api's consumer
important for data hiding + beautification
 */
package com.sg.m2a.models;

import java.time.LocalDateTime;

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
}
