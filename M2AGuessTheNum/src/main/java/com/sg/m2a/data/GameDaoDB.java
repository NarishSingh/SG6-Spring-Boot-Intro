package com.sg.m2a.data;

import com.sg.m2a.data.RoundDaoDB.RoundMapper;
import com.sg.m2a.models.Game;
import com.sg.m2a.models.Round;
import java.sql.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class GameDaoDB implements GameDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Game createGame(Game newGame) {
        String gameQuery = "INSERT INTO game(answer) "
                + "VALUES(?);";

        //insert w answer, default will initialize isFinished to false
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(((conn) -> {
            PreparedStatement stmt = conn.prepareStatement(gameQuery, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, newGame.getAnswer());

            return stmt;
        }), keyHolder);

        //grab ai id
        newGame.setGameId(keyHolder.getKey().intValue());

        return newGame;
    }

    @Override
    public Game readGameById(int id) {
        try {
            String readQuery = "SELECT * FROM game "
                    + "WHERE gameId = ?;";

            return jdbc.queryForObject(readQuery, new GameMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Game> readAllGames() {
        String readAllQuery = "SELECT * FROM game;";

        return jdbc.query(readAllQuery, new GameMapper());
    }

    @Override
    public boolean updateGame(Game game) {
        String updateQuery = "UPDATE game "
                + "SET isFinished = ?;";
        
        associateRoundsWithGame(game);
        
        return jdbc.update(updateQuery, game.isIsFinished()) == 1; //true iff that game row effected
    }

    @Override
    public boolean deleteGameById(int id) {
        String deleteQuery = "DELETE FROM game "
                + "WHERE gameId = ?;";

        return jdbc.update(deleteQuery, id) > 0;
    }

    @Override
    public List<Round> associateRoundsWithGame(Game game) {
        String roundsForGameQuery = "SELECT * FROM round "
                + "WHERE gameId = ?;";
        
        List<Round> rounds = jdbc.query(roundsForGameQuery, 
                new RoundMapper(), game.getGameId());
        
        game.setRounds(rounds); //update rounds to POJO not db
        
        return rounds;
    }

    /**
     * RowMapper implementation
     */
    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int rowNum) throws SQLException {
            Game g = new Game();
            g.setGameId(rs.getInt("gameId"));
            g.setAnswer(rs.getString("answer"));
            g.setIsFinished(rs.getBoolean("isFinished")); //should work now that its bit

            return g;
        }

    }

}
