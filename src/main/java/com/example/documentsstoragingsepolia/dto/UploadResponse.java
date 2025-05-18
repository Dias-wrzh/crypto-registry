package com.example.documentsstoragingsepolia.dto;

import java.util.UUID;

public record UploadResponse(UUID id, String hash, boolean registered) {}
