package org.danielsa.proiect_ps.models;

import java.util.ArrayList;

public interface AdminModelInterface {
    ArrayList<User> getUsers();
    User updateUser(String username, String newUsername, String newPassword, String newUserType);
    boolean deleteUser(String username);
    User getUserByUsername(String username);
    boolean register(String username, String password, String usertype);
}