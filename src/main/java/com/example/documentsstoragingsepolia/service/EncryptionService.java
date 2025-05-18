package com.example.documentsstoragingsepolia.service;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionService {
    private static final String ENCRYPTION_ALGO = "AES/GCM/NoPadding";
    private static final int KEY_SIZE = 256;
    private static final int IV_SIZE = 12;
    private static final int TAG_LENGTH = 128;


    private final SecretKey secretKey;

    public EncryptionService(String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        this.secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public byte[] encrypt(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGO);
        byte[] iv = new byte[IV_SIZE];
        new SecureRandom().nextBytes(iv);

        GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);

        byte[] encrypted = cipher.doFinal(data);

        // IV + encrypted = итоговый файл
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(iv);
        outputStream.write(encrypted);
        return outputStream.toByteArray();
    }

    public byte[] decrypt(byte[] encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGO);
        byte[] iv = new byte[IV_SIZE];
        System.arraycopy(encryptedData, 0, iv, 0, IV_SIZE);

        GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);

        byte[] ciphertext = new byte[encryptedData.length - IV_SIZE];
        System.arraycopy(encryptedData, IV_SIZE, ciphertext, 0, ciphertext.length);

        return cipher.doFinal(ciphertext);
    }

    // Используй один раз для генерации ключа
    public static String generateBase64Key() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(KEY_SIZE);
        SecretKey key = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}

