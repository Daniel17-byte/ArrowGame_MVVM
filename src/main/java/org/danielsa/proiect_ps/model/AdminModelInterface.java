package org.danielsa.proiect_ps.model;

import java.util.ArrayList;

public interface AdminModelInterface {
    ArrayList<UserModel> getUsers();
    UserModel updateUser(String username, String newUsername, String newPassword, String newUserType);
    boolean deleteUser(String username);
    UserModel getUserByUsername(String username);
    boolean register(String username, String password, String usertype);
}