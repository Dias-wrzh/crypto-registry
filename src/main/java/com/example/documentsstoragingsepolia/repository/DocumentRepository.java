package com.example.documentsstoragingsepolia.repository;

import com.example.documentsstoragingsepolia.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {

}