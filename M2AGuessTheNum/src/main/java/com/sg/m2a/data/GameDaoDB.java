/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.m2a.data;

import com.sg.m2a.models.Game;
import com.sg.m2a.models.Round;
import java.sql.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Game> readAllGames() {
        String readAllQuery = "SELECT * FROM game;";

        return jdbc.query(readAllQuery, new GameMapper());
    }

    @Override
    public boolean updateGame(Game game) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteGameById(int id) {
        String deleteQuery = "DELETE FROM game "
                + "WHERE gameId = ?;";

        return jdbc.update(deleteQuery, id) > 0;
    }

    @Override
    public List<Round> readRoundsForGame(Game game) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            g.setIsFinished(rs.getBoolean("isFinished")); //FIXME verify if this is right

            return g;
        }

    }

}
