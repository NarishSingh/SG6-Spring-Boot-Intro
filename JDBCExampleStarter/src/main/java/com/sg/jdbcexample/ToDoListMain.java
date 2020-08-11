package com.sg.jdbcexample;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javax.sql.DataSource;

/**
 *
 * @author kylerudy
 */
public class ToDoListMain {

    private static Scanner sc;
    private static DataSource ds;

    public static void main(String[] args) {
        sc = new Scanner(System.in);

        //connect to db - todoDB
        try {
            ds = getDataSource();
        } catch (SQLException e) {
            System.out.println("Error connecting");
            System.out.println(e.getMessage());
            System.exit(0);
        }

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
                }
            } catch (SQLException ex) {
                System.out.println("Error communicating with database");
                System.out.println(ex.getMessage());
                System.exit(0);
            }

        } while (true);
    }

    private static DataSource getDataSource() throws SQLException {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName("localhost");
        ds.setDatabaseName("todoDB");
        ds.setUser("root");
        //FIXME fill in password and uncomment
//        ds.setPassword("");
        ds.setServerTimezone("EST");
        ds.setUseSSL(false);
        ds.setAllowPublicKeyRetrieval(true);

        return ds;
    }

    private static void displayList() throws SQLException {
        try (Connection conn = ds.getConnection()) {
            //DAO
            Statement stmt = conn.createStatement();
            String displayQuery = "SELECT * FROM todo";
            ResultSet rs = stmt.executeQuery(displayQuery);

            //VIEW
            while (rs.next()) {
                System.out.printf("%s: %s -- %s -- %s\n",
                        rs.getString("id"),
                        rs.getString("todo"),
                        rs.getString("note"),
                        rs.getBoolean("finished"));
            }

            System.out.println("");
        }
    }

    private static void addItem() throws SQLException {
        //VIEW
        System.out.println("Add Item");
        System.out.println("What is the task?");
        String task = sc.nextLine();
        System.out.println("Any additional notes?");
        String note = sc.nextLine();

        //DAO
        try (Connection conn = ds.getConnection()) {
            String addQuery = "INSERT INTO todo(todo, note) VALUES(?,?)";
            PreparedStatement pStmt = conn.prepareCall(addQuery);
            pStmt.setString(1, task);
            pStmt.setString(2, note);
            pStmt.executeUpdate();
            System.out.println("Add Complete");
        }
    }

    private static void updateItem() throws SQLException {
        //VIEW
        System.out.println("Update Item");
        System.out.println("Which item do you want to update?");
        String itemId = sc.nextLine();

        //DAO
        try (Connection conn = ds.getConnection()) {
            String updateQuery = "SELECT * FROM todo WHERE id = ?";
            PreparedStatement pStmt = conn.prepareCall(updateQuery);
            pStmt.setString(1, itemId);
            ResultSet rs = pStmt.executeQuery();
            rs.next();
            ToDo td = new ToDo();
            td.setId(rs.getInt("id"));
            td.setTodo(rs.getString("todo"));
            td.setNote(rs.getString("note"));
            td.setFinished(rs.getBoolean("finished"));

            //VIEW
            System.out.println("1. ToDo - " + td.getTodo());
            System.out.println("2. Note - " + td.getNote());
            System.out.println("3. Finished - " + td.isFinished());
            System.out.println("What would you like to change?");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Enter new ToDo:");
                    String todo = sc.nextLine();
                    td.setTodo(todo);
                    break;
                case "2":
                    System.out.println("Enter new Note:");
                    String note = sc.nextLine();
                    td.setNote(note);
                    break;
                case "3":
                    System.out.println("Toggling Finished to " + !td.isFinished());
                    td.setFinished(!td.isFinished());
                    break;
                default:
                    System.out.println("No change made");
                    return;
            }

            //DAO
            String updateSql = "UPDATE todo SET todo = ?, note = ?, finished = ? WHERE id = ?";
            PreparedStatement updatePStmt = conn.prepareCall(updateSql);
            updatePStmt.setString(1, td.getTodo());
            updatePStmt.setString(2, td.getNote());
            updatePStmt.setBoolean(3, td.isFinished());
            updatePStmt.setInt(4, td.getId());
            updatePStmt.executeUpdate();
            System.out.println("Update Complete");
        }
    }

    private static void removeItem() throws SQLException {
        //VIEW
        System.out.println("Remove Item");
        System.out.println("Which item would you like to remove?");
        String itemId = sc.nextLine();
        
        //DAO
        try(Connection conn = ds.getConnection()) {
            String removeQuery = "DELETE FROM todo "
                    + "WHERE id = ?";
            PreparedStatement pStmt = conn.prepareCall(removeQuery);
            pStmt.setString(1, itemId);
            pStmt.executeUpdate();
            System.out.println("Remove Complete");
        }    
    }
}
