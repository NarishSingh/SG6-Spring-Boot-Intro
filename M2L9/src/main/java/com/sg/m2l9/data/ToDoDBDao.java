/*
todoDB profile
 */
package com.sg.m2l9.data;

import com.sg.m2l9.models.ToDo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@Profile("database")
public class ToDoDBDao implements ToDoDao {

    private final JdbcTemplate jdbc;

    @Autowired
    public ToDoDBDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public ToDo add(ToDo todo) {
        String addQuery = "INSERT INTO todo(todo, note) "
                + "VALUES(?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        //uses a PreparedStatementCreator
        jdbc.update(((conn) -> {
            PreparedStatement stmt = conn.prepareStatement(addQuery, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, todo.getTodo());
            stmt.setString(2, todo.getNote());
            return stmt;
        }), keyHolder);

        todo.setId(keyHolder.getKey().intValue());

        return todo;
    }

    @Override
    public List<ToDo> getAll() {
        String readAllQuery = "SELECT id, todo, note, finished "
                + "FROM todo";

        return jdbc.query(readAllQuery, new TodoMapper());
    }

    @Override
    public ToDo findById(int id) {
        String readQuery = "SELECT id, todo, note, finished "
                + "FROM todo "
                + "WHERE id = ?;";

        return jdbc.queryForObject(readQuery, new TodoMapper(), id);
    }

    @Override
    public boolean update(ToDo todo) {
        String updateQuery = "UPDATE todo SET "
                + "todo = ?, "
                + "note = ?, "
                + "finished = ? "
                + "WHERE id = ?;";

        return jdbc.update(updateQuery,
                todo.getTodo(),
                todo.getNote(),
                todo.isFinished(),
                todo.getId()) > 0; //try update, but return a boolean if successful or not
    }

    @Override
    public boolean deleteById(int id) {
        String deleteQuery = "DELETE FROM todo "
                + "WHERE id = ?;";

        return jdbc.update(deleteQuery, id) > 0;
    }

    private static final class TodoMapper implements RowMapper<ToDo> {

        @Override
        public ToDo mapRow(ResultSet rs, int index) throws SQLException {
            ToDo td = new ToDo();
            td.setId(rs.getInt("id"));
            td.setTodo(rs.getString("todo"));
            td.setNote(rs.getString("note"));
            td.setFinished(rs.getBoolean("finished"));
            return td;
        }
    }

}
