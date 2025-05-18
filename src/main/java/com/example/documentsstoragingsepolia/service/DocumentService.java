package com.example.documentsstoragingsepolia.service;

import com.example.documentsstoragingsepolia.dto.DocumentDownload;
import com.example.documentsstoragingsepolia.dto.UploadResponse;
import com.example.documentsstoragingsepolia.model.Document;
import com.example.documentsstoragingsepolia.repository.DocumentRepository;
import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.UUID;

@Slf4j
@Service
public class DocumentService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private BlockchainService blockchainService;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private EncryptionService encryptionService;

    private final String bucket = "documents";

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public UploadResponse uploadDocument(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Документ пустой");
        }

        UUID id = UUID.randomUUID();
        String key = id.toString();
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();

        byte[] originalBytes = file.getBytes();
        byte[] hash = sha256Bytes(originalBytes);
        String stringHash = toHex(hash);
        byte[] encryptedBytes = encryptionService.encrypt(originalBytes);
        InputStream encryptedStream = new ByteArrayInputStream(encryptedBytes);

        Document doc = Document.builder()
                .id(id)
                .name(fileName)
                .contentType(contentType)
                .minioKey(key)
                .sha256(stringHash)
                .build();
        System.out.println(stringHash);
        System.out.println(doc);
        // Save metadata to DB
        documentRepository.save(doc);

        // Upload to MinIO
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(id.toString())
                        .stream(encryptedStream, encryptedBytes.length, -1)
                        .contentType("application/octet-stream")
                        .build()
        );

        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucket)
                            .object(id.toString())
                            .build()
            );
        } catch (Exception e) {
            throw new IllegalStateException("Файл не загружен в хранилище", e);
        }

        blockchainService.registerHash(hash);
        log.info("Registered hash {} in blockchain", stringHash);

        return new UploadResponse(id, stringHash, true);
    }

    public DocumentDownload downloadDocument(UUID id) throws Exception {
        Document doc = documentRepository.findById(id).orElseThrow();
        InputStream stream = minioClient.getObject(
                GetObjectArgs.builder().bucket(bucket).object(id.toString()).build()
        );
        byte[] encryptedContent = stream.readAllBytes();

        byte[] decryptedContent = encryptionService.decrypt(encryptedContent);

        return new DocumentDownload(doc.getName(), doc.getContentType(), decryptedContent);
    }

    @Transactional
    public void deleteDocument(UUID id) throws Exception {
        documentRepository.deleteById(id);
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(id.toString()).build());
    }

    public boolean verifyHashInBlockchain(String hexHash) throws Exception {
        byte[] bytes = hexStringToByteArray(hexHash);
        return blockchainService.isHashRegistered(bytes);
    }

    public boolean verifyFileInBlockchain(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Документ пустой");
        }
        byte[] bytes = file.getBytes();
        byte[] hash = sha256Bytes(bytes);
        String stringHash = toHex(hash);
        return verifyHashInBlockchain(stringHash);
    }


    private byte[] hexStringToByteArray(String hex) {
        int len = hex.length();
        byte[] result = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            result[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return result;
    }

    private byte[] sha256Bytes(byte[] input) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(input); // вернёт именно byte[32]
    }

    private String toHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
