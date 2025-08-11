package com.sweetk.iitp.api.util;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

/**
 * Jasypt 암호화 유틸리티 클래스
 * PBEWITHHMACSHA512ANDAES_256 알고리즘을 사용하여 문자열을 암호화합니다.
 */
public class JasyptEncryptor {
    
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java JasyptEncryptor <plain-text> <password>");
            System.exit(1);
        }
        
        String plainText = args[0];
        String password = args[1];
        
        try {
            String encryptedText = encrypt(plainText, password);
            System.out.println("plain-text = [" + plainText + "]");
            System.out.println("Encrypt-Key = [" + password + "]");
            System.out.println("algorithm = PBEWITHHMACSHA512ANDAES_256");
            System.out.println("Encrypted text: " + encryptedText);
        } catch (Exception e) {
            System.err.println("Encryption failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public static String encrypt(String plainText, String password) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        
        config.setPassword(password);
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setKeyObtentionIterations(1000);
        config.setPoolSize(1);
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        
        encryptor.setConfig(config);
        
        return encryptor.encrypt(plainText);
    }
}
