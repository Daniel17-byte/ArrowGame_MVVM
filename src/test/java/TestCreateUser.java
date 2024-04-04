import org.danielsa.proiect_ps.DatabaseService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCreateUser {
    DatabaseService databaseService = new DatabaseService();
    @Test
    public void testRegister() {
        double rand = Math.random() * LocalDateTime.now().getSecond();
        String username = "testUser" + rand;
        String password = "testPassword";
        String userType = "PLAYER";

        assertTrue(databaseService.register(username, password, userType));
        assertTrue(databaseService.authenticate(username, password));
        assertTrue(databaseService.deleteUser(username));

        System.out.println("Test : Create User");
    }
}
