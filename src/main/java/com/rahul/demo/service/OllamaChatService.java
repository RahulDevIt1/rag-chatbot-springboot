package com.rahul.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OllamaChatService {

    @Autowired
    private RestTemplate restTemplate;

    public String ask(String prompt) {

        String url = "http://localhost:11434/api/generate";

        Map<String, Object> body = new HashMap<>();
        body.put("model", "phi3");   // or "phi3"
        body.put("prompt", prompt);
        body.put("stream", false);

        Map response = restTemplate.postForObject(url, body, Map.class);

        return (String) response.get("response");
    }
}