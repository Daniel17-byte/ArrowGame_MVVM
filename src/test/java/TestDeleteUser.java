import org.danielsa.proiect_ps.DatabaseService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TestDeleteUser {
    DatabaseService databaseService = new DatabaseService();
    @Test
    public void testDeleteUser() {
        double rand = Math.random() * LocalDateTime.now().getSecond();
        String username = "testUser" + rand;
        String password = "testPassword";
        String userType = "regular";
        databaseService.register(username, password, userType);

        assertTrue(databaseService.deleteUser(username));

        assertFalse(databaseService.authenticate(username, password));

        System.out.println("Test : Delete User");
    }
}