package org.danielsa.proiect_ps.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
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
}
