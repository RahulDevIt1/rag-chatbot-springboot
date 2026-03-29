package com.rahul.demo.service.geminiService;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;




@Service
public class GeminiChatService {

    @org.springframework.beans.factory.annotation.Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String ask(String prompt) {
        // Use supported model from listModels. As of current API, text-bison-001 is broadly available.
        String model = "text-bison-001";
        String url = "https://generativelanguage.googleapis.com/v1/models/" + model + ":generateText?key=" + apiKey;

        Map<String, Object> body = Map.of(
            "prompt", Map.of("text", prompt),
            "temperature", 0.2,
            "maxOutputTokens", 800
        );

        Map response = restTemplate.postForObject(url, body, Map.class);

        // For text-bison and similar endpoints, response may have "candidates" -> "output".
        if (response.containsKey("candidates")) {
            List candidates = (List) response.get("candidates");
            if (!candidates.isEmpty()) {
                Map first = (Map) candidates.get(0);
                Object output = first.get("output");
                if (output != null) {
                    return output.toString();
                }
            }
        }

        // Legacy gemini response handling fallback:
        if (response.containsKey("candidates")) {
            List candidates = (List) response.get("candidates");
            if (!candidates.isEmpty()) {
                Map first = (Map) candidates.get(0);
                Map content = (Map) first.get("content");
                if (content != null && content.containsKey("parts")) {
                    List parts = (List) content.get("parts");
                    if (!parts.isEmpty()) {
                        return (String) ((Map) parts.get(0)).get("text");
                    }
                }
            }
        }

        throw new IllegalStateException("Unexpected response format from model: " + response);
    }
}