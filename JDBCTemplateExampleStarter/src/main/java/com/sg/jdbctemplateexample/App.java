package com.sg.jdbctemplateexample;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author kylerudy
 */
@SpringBootApplication
public class App implements CommandLineRunner {

    private static Scanner sc;

    @Autowired
    private JdbcTemplate jdbc;

    public static void main(String args[]) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        sc = new Scanner(System.in);

        do {
            System.out.println("To-Do List");
            System.out.println("1. Display List");
            System.out.println("2. Add Item");
            System.out.println("3. Update Item");
            System.out.println("4. Remove Item");
            System.out.println("5. Exit");

            System.out.println("Enter an option:");
            String option = sc.nextLine();
            try {
                switch (option) {
                    case "1":
                        displayList();
                        break;
                    case "2":
                        addItem();
                        break;
                    case "3":
                        updateItem();
                        break;
                    case "4":
                        removeItem();
                        break;
                    case "5":
                        System.out.println("Exiting");
                        System.exit(0);
                    default:
                        System.out.println("I don't understand");
                }
            } catch (Exception ex) {
                System.out.println("Error communicating with database");
                System.out.println(ex.getMessage());
                System.exit(0);
            }

        } while (true);
    }

    private static final class ToDoMapper implements RowMapper<ToDo> {

        @Override
        public ToDo mapRow(ResultSet rs, int index) throws SQLException {
            ToDo td = new ToDo();
            td.setId(rs.getInt("id"));
            td.setTodo(rs.getString("todo"));
            td.setNote(rs.getString("note"));
            td.setFinished(rs.getBoolean("finished"));

//            rs.getObject("theDate", LocalDate.class); //how to grab and parse a LocalDate obj
            return td;
        }
    }

    private void displayList() throws SQLException {
        //DAO
        String query = "SELECT * FROM todo";
        List<ToDo> todos = jdbc.query(query, new ToDoMapper());

        //VIEW
        for (ToDo td : todos) {
            System.out.printf("%s: %s -- %s -- %s\n",
                    td.getId(),
                    td.getTodo(),
                    td.getNote(),
                    td.isFinished());
        }
        System.out.println("");
    }

    private void addItem() throws SQLException {
        //VIEW
        System.out.println("Add Item");
        System.out.println("What is the task?");
        String task = sc.nextLine();
        System.out.println("Any additional notes?");
        String note = sc.nextLine();

        ToDo todo = new ToDo();
        todo.setTodo(task);
        todo.setNote(note);

        //DAO
        String query = "INSERT INTO todo(todo, note) VALUES(?,?)";
        jdbc.update(query, todo.todo, todo.note);
        System.out.println("Add Complete");
    }

    private void updateItem() throws SQLException {
        //VIEW
        System.out.println("Update Item");
        System.out.println("Which item do you want to update?");
        String itemId = sc.nextLine();
        
        //DAO - read by id
        String query = "SELECT * FROM todo WHERE id = ?";
        ToDo item = jdbc.queryForObject(query, new ToDoMapper(), itemId);

        //VIEW
        System.out.println("1. ToDo - " + item.getTodo());
        System.out.println("2. Note - " + item.getNote());
        System.out.println("3. Finished - " + item.isFinished());
        System.out.println("What would you like to change?");
        String choice = sc.nextLine();
        switch (choice) {
            case "1":
                System.out.println("Enter new ToDo:");
                String todo = sc.nextLine();
                item.setTodo(todo);
                break;
            case "2":
                System.out.println("Enter new Note:");
                String note = sc.nextLine();
                item.setNote(note);
                break;
            case "3":
                System.out.println("Toggling Finished to " + !item.isFinished());
                item.setFinished(!item.isFinished());
                break;
            default:
                System.out.println("No change made");
                return;
        }

        //DAO (Update)
        String updateQuery = "UPDATE todo "
                + "SET todo = ?, note = ?, finished = ? "
                + "WHERE id = ?"; //format for ease of use

        jdbc.update(updateQuery,
                item.getTodo(),
                item.getNote(),
                item.isFinished(),
                item.getId());
        
        System.out.println("Update Complete");
    }

    private void removeItem() throws SQLException {
        //VIEW
        System.out.println("Remove Item");
        System.out.println("Which item would you like to remove?");
        String itemId = sc.nextLine();
        
        //DAO
        String deleteQuery = "DELETE FROM todo WHERE id = ?";
        jdbc.update(deleteQuery, itemId);
        System.out.println("Remove Complete");
    }

}
