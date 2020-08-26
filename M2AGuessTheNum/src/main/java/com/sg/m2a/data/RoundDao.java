package com.sg.m2a.data;

import com.sg.m2a.models.Round;
import java.util.List;

public interface RoundDao {

    /**
     * Start a new round of a game
     *
     * @param newRound {Round} a properly formed Round obj
     * @return {Round} the successfully added Round obj
     */
    Round createRound(Round newRound);

    /**
     * Find a round by its id
     *
     * @param id {int} id for a completed round
     * @return {Round} the obj at that pk
     */
    Round readRoundById(int id);

    /**
     * Get all rounds played, regardless of game
     *
     * @return {List} all rounds played to date
     */
    List<Round> readAllRounds();
    
    /**
     * Update a round
     *
     * @param round {Round} the replacement obj to be put in db
     * @return {boolean} true if round exists and is updated
     */
    boolean updateRound(Round round);

    /**
     * Delete a round
     *
     * @param id {int} id of an existing round
     * @return {boolean} true if round exists and is deleted
     */
    boolean deleteRoundByID(int id);

}
