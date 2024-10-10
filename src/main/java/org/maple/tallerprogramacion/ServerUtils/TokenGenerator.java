package org.maple.tallerprogramacion.ServerUtils;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class TokenGenerator {

    // Method to generate a UUID token
    public String generateUUIDToken() {
        return UUID.randomUUID().toString();
    }

    // Method to generate a secure random token
    public String generateSecureToken(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[length];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
}