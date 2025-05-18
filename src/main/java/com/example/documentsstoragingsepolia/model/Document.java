package com.example.documentsstoragingsepolia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "minio_key", nullable = false)
    private String minioKey;

    private String sha256;

    @Column(name = "uploaded_at", nullable = false)
    @Builder.Default
    private LocalDateTime uploadedAt = LocalDateTime.now();
}
