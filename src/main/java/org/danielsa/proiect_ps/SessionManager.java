package org.danielsa.proiect_ps;

import org.danielsa.proiect_ps.model.UserModel;

import java.util.*;

public class SessionManager {
    private static final Map<String, UserModel> sessions = new HashMap<>();

    public static void createSession(UserModel user) {
        String sessionId = generateSessionId();
        sessions.put(sessionId, user);
    }

    @SuppressWarnings(value = "unused")
    public static boolean isValidSession(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    @SuppressWarnings(value = "unused")
    public static UserModel getUsernameForSession(String sessionId) {
        return sessions.get(sessionId);
    }

    private static String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}
