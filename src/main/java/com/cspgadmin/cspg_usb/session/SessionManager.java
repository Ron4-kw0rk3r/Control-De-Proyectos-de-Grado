package com.cspgadmin.cspg_usb.session;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

@Component
public class SessionManager {
    private final Map<String, SessionInfo> sessions = new ConcurrentHashMap<>();
    
    public String createSession(Long userId) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, new SessionInfo(userId));
        return sessionId;
    }
    
    public boolean isValidSession(String sessionId) {
        return sessions.containsKey(sessionId);
    }
    
    public void invalidateSession(String sessionId) {
        sessions.remove(sessionId);
    }
    
    private static class SessionInfo {
        private final Long userId;
        private final long creationTime;
        
        public SessionInfo(Long userId) {
            this.userId = userId;
            this.creationTime = System.currentTimeMillis();
        }
    }
} 