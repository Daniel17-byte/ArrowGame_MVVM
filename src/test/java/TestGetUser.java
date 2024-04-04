import org.danielsa.proiect_ps.models.User;
import org.danielsa.proiect_ps.DatabaseService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGetUser {
    DatabaseService databaseService = new DatabaseService();
    @Test
    public void testGetUser() {
        double rand = Math.random() * LocalDateTime.now().getSecond();
        String username = "testUser" + rand;
        String password = "testPassword";
        String userType = "PLAYER";

        assertTrue(databaseService.register(username, password, userType));
        assertTrue(databaseService.authenticate(username, password));

        ArrayList<User> users = databaseService.getUsers();
        HashMap<String, User> map = new HashMap<>();
        users.forEach( u -> map.put(u.getUserName(), u));

        assertTrue(map.containsKey(username));
        assertTrue(databaseService.deleteUser(username));

        System.out.println("Test : Get User");
    }
}
