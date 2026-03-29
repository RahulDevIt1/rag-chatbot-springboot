package com.rahul.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rahul.demo.model.DocumentChunk;

public interface DocumentRepository extends MongoRepository<DocumentChunk,String> {

    List<DocumentChunk> findByContentContainingIgnoreCase(String keyword);
}
