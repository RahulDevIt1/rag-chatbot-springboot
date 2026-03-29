package com.rahul.demo.service.geminiService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiEmbeddingService {

    @Autowired
    private RestTemplate restTemplate;

    public List<Double> getEmbedding(String text) {

        String url = "http://localhost:11434/api/embeddings";

        Map<String, Object> body = new HashMap<>();
        body.put("model", "nomic-embed-text");
        body.put("prompt", text);

        Map response = restTemplate.postForObject(url, body, Map.class);

        // Ollama returns embedding directly, NOT inside "values"
        return (List<Double>) response.get("embedding");
    }
}
