package com.rahul.demo.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Document(collection= "documents")
public class DocumentChunk {

    @Id
    private String id;

    private String content;
    private String filename;

    private List<Double> embedding;

    public DocumentChunk() {}
    public DocumentChunk(String content, String filename) {
        this.content = content;
        this.filename = filename;
    }

    public List<Double> getEmbedding() { return embedding; }
    public void setEmbedding(List<Double> embedding) { this.embedding = embedding; }
    public String getContent() {
    return content;
}

}
