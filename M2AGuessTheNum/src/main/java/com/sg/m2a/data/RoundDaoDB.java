package com.sg.m2a.data;

import com.sg.m2a.models.Round;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
        newRound.setTime(LocalDateTime.now().withNano(0));

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
        try {
            String readQuery = "SELECT * FROM round "
                    + "WHERE roundId = ?;";

            return jdbc.queryForObject(readQuery, new RoundMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Round> readAllRounds() {
        String readAllQuery = "SELECT * FROM round;";

        return jdbc.query(readAllQuery, new RoundMapper());
    }

    @Override
    public boolean updateRound(Round roundUpdate) {
        String updateQuery = "UPDATE round "
                + "SET guess = ?, digitMatches = ? "
                + "WHERE roundId = ?;";

        return jdbc.update(updateQuery,
                roundUpdate.getGuess(),
                roundUpdate.getDigitMatches(),
                roundUpdate.getRoundId()) == 1; //true if only that round row effected
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
//            r.setTime(rs.getObject("time", LocalDateTime.class)); //if line 99 doesn't work use this
            r.setDigitMatches(rs.getString("digitMatches"));

            return r;
        }
    }

}
