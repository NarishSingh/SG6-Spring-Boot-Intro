package com.sg.m2a.data;

import com.sg.m2a.models.Round;

public interface RoundDao {

    /**
     * Start a new round of a game
     * @param newRound
     * @return 
     */
    Round createRound(Round newRound);

    Round readRoundById(int id);

    boolean updateRound(Round round);

    boolean deleteRoundByID(int id);
    
}
