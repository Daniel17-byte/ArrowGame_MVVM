package org.danielsa.proiect_ps.presenters;

import lombok.Getter;
import org.danielsa.proiect_ps.models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseService {
    private Connection connection;

    @Getter
    private User user;

    public DatabaseService() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/arrows", "root", "adminpass");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean authenticate(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String usrN = resultSet.getString("username");
                String usrT = resultSet.getString("userType");
                int gamesWon = resultSet.getInt("gameswon");

                user = new User(usrN,usrT,gamesWon);
                SessionManager.createSession(user);
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean register(String username, String password, String usertype) {
        String query = "INSERT INTO users (username, password, usertype) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, usertype);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                user = new User(username, usertype, 0);
                SessionManager.createSession(user);
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        String query = "SELECT * FROM users";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String usrN = resultSet.getString("username");
                String usrT = resultSet.getString("userType");
                int gamesWon = resultSet.getInt("gameswon");
                users.add(new User(usrN,usrT,gamesWon));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

    public void updateUserScore() {
        String query = "UPDATE users SET gameswon = gameswon + 1 WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, getUser().getUserName());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
