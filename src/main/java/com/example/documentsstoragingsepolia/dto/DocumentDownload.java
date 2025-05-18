package com.example.documentsstoragingsepolia.dto;

public record DocumentDownload(
        String name,
        String contentType,
        byte[] content
) {}
