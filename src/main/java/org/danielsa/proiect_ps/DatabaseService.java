package org.danielsa.proiect_ps;

import lombok.Getter;
import org.danielsa.proiect_ps.model.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
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

    public User updateUser(String username, String newUsername, String newPassword, String newUserType) {
        String query = "UPDATE users SET username = ?, password = ?, usertype = ? WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newUsername);
            statement.setString(2, newPassword);
            statement.setString(3, newUserType);
            statement.setString(4, username);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return new User(newUsername, newUserType, getUser().getGamesWon());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean deleteUser(String username) {
        String query = "DELETE FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void updateUserScore() {
        String query = "UPDATE users SET gameswon = gameswon + 1 WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, getUser().getUserName());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        user = getUserByUsername(user.getUserName());
    }

    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String usrN = resultSet.getString("username");
                String usrT = resultSet.getString("userType");
                int gamesWon = resultSet.getInt("gameswon");
                return new User(usrN, usrT, gamesWon);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
