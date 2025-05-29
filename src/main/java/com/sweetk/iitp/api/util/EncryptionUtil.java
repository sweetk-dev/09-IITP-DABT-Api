package com.sweetk.iitp.api.util;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class EncryptionUtil {

    private static final String ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;

    public String hashApiKey(String apiKey) {
        try {
            // Generate a random salt
            byte[] salt = new byte[SALT_LENGTH];
            new SecureRandom().nextBytes(salt);

            // Create MessageDigest instance
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            
            // Add salt to the digest
            md.update(salt);
            
            // Get the hash's bytes
            byte[] hashedBytes = md.digest(apiKey.getBytes(StandardCharsets.UTF_8));

            // Combine salt and hash
            byte[] combined = new byte[salt.length + hashedBytes.length];
            System.arraycopy(salt, 0, combined, 0, salt.length);
            System.arraycopy(hashedBytes, 0, combined, salt.length, hashedBytes.length);

            // Convert to Base64 string
            return Base64.getEncoder().encodeToString(combined);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing API key", e);
        }
    }

    public boolean verifyApiKey(String apiKey, String hashedApiKey) {
        try {
            // Decode the stored hash
            byte[] combined = Base64.getDecoder().decode(hashedApiKey);
            
            // Extract the salt
            byte[] salt = new byte[SALT_LENGTH];
            System.arraycopy(combined, 0, salt, 0, SALT_LENGTH);
            
            // Create MessageDigest instance
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            
            // Add salt to the digest
            md.update(salt);
            
            // Get the hash's bytes
            byte[] hashedBytes = md.digest(apiKey.getBytes(StandardCharsets.UTF_8));
            
            // Compare the hashes
            return MessageDigest.isEqual(hashedBytes, 
                java.util.Arrays.copyOfRange(combined, SALT_LENGTH, combined.length));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error verifying API key", e);
        }
    }
} 