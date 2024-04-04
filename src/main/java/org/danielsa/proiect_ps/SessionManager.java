package org.danielsa.proiect_ps;

import org.danielsa.proiect_ps.models.User;

import java.util.*;

public class SessionManager {
    private static final Map<String, User> sessions = new HashMap<>();

    public static void createSession(User user) {
        String sessionId = generateSessionId();
        sessions.put(sessionId, user);
    }

    @SuppressWarnings(value = "unused")
    public static boolean isValidSession(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    @SuppressWarnings(value = "unused")
    public static User getUsernameForSession(String sessionId) {
        return sessions.get(sessionId);
    }

    private static String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}
