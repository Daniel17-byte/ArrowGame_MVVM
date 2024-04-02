package org.danielsa.proiect_ps.models;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    @Getter
    private String userName;
    private String password;
    private UserType userType;
    private int gamesWon;

    public User(String userName, String usertype, int gamesWon) {
        this.userName = userName;
        if (usertype.equals("ADMIN")){
            this.userType = UserType.ADMIN;
        }else {
            this.userType = UserType.PLAYER;
        }
        this.gamesWon = gamesWon;
    }

    @Override
    public String toString() {
        return userName + " : " + gamesWon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return gamesWon == user.gamesWon && Objects.equals(id, user.id) && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && userType == user.userType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, userType, gamesWon);
    }
}
