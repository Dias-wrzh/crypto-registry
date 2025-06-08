package com.example.documentsstoragingsepolia.controller;

import com.example.documentsstoragingsepolia.dto.DocumentDownload;
import com.example.documentsstoragingsepolia.dto.UploadResponse;
import com.example.documentsstoragingsepolia.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        UploadResponse response = documentService.uploadDocument(file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> download(@PathVariable UUID id) throws Exception {
        DocumentDownload dto = documentService.downloadDocument(id);
        String filename = dto.name();
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"download.pdf\"; filename*=UTF-8''" + encodedFilename)
                .contentType(MediaType.parseMediaType(dto.contentType()))
                .body(dto.content());
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam("hash") String hash) throws Exception {
        boolean found = documentService.verifyHashInBlockchain(hash);
        return ResponseEntity.ok(found ? "Hash found" : "Hash not found");
    }
    @GetMapping("/verifyFile")
    public ResponseEntity<String> verifyFile(@RequestParam("file") MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        boolean found = documentService.verifyFileInBlockchain(file);
        return ResponseEntity.ok(found ? "Hash found" : "Hash not found");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) throws Exception {
        documentService.deleteDocument(id);
        return ResponseEntity.ok("Deleted");
    }

}
