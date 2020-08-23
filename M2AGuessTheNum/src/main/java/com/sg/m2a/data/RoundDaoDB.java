package com.sg.m2a.data;

import com.sg.m2a.models.Round;
import java.sql.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RoundDaoDB implements RoundDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Round createRound(Round newRound) {
        //select and insert to game first
        /*
        String selectGameQuery = "SELECT gameId, answer, isFinished FROM game "
                + "WHERE gameId = ?;";
        Game roundGame = jdbc.queryForObject(selectGameQuery, new GameMapper(), newRound.getGameId());
        
        String gameQuery = "INSERT INTO game(gameId, answer, isFinished) "
                + "VALUES(?,?,?);";
        jdbc.update(gameQuery, 
                roundGame.getGameId(),
                roundGame.getAnswer(),
                roundGame.isIsFinished());
         */

        //insert to db using a keyholder
        String insertQuery = "INSERT INTO round(gameId, guess, time, digitMatches) "
                + "VALUES(?,?,?,?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(((conn) -> {
            PreparedStatement stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, newRound.getGameId());
            stmt.setString(2, newRound.getGuess());
            stmt.setTimestamp(3, Timestamp.valueOf(newRound.getTime()));
            stmt.setString(4, newRound.getDigitMatches());

            return stmt;
        }), keyHolder);

        //grab ai id
        newRound.setRoundId(keyHolder.getKey().intValue());

        return newRound;
    }

    @Override
    public Round readRoundById(int id) {
        String readQuery = "SELECT roundId, gameId, guess, time, digitMatches FROM round "
                + "WHERE roundId = ?;";

        return jdbc.queryForObject(readQuery, new RoundMapper(), id);
    }

    @Override
    public List<Round> readAllRounds() {
        String readAllQuery = "SELECT * FROM round;";

        return jdbc.query(readAllQuery, new RoundMapper());
    }

    @Override
    public boolean updateRound(Round round) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteRoundByID(int id) {
        String deleteQuery = "DELETE FROM round "
                + "WHERE roundId = ?;";

        return jdbc.update(deleteQuery, id) > 0;
    }

    /**
     * RowMapper implementation
     */
    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round r = new Round();
            r.setRoundId(rs.getInt("roundId"));
            r.setGameId(rs.getInt("gameId"));
            r.setGuess(rs.getString("guess"));
            r.setTime(rs.getTimestamp("time").toLocalDateTime());
            r.setDigitMatches(rs.getString("digitMatches"));

            return r;
        }
    }

}
