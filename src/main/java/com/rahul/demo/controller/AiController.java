package com.rahul.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rahul.demo.model.DocumentChunk;
import com.rahul.demo.repository.DocumentRepository;
import com.rahul.demo.service.AiService;
import com.rahul.demo.service.OllamaChatService;
import com.rahul.demo.service.PdfService;
import com.rahul.demo.service.SimilarityService;
import com.rahul.demo.service.TextSplitter;
import com.rahul.demo.service.geminiService.GeminiEmbeddingService;

@RestController
@RequestMapping("/ai")
public class AiController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private TextSplitter textSplitter;

    @Autowired
    private DocumentRepository documentRepository;  

    

    @Autowired
    private SimilarityService similarityService;

    @Autowired
    private AiService aiService;

    @Autowired
    private GeminiEmbeddingService embeddingService;

    @GetMapping("/test")
    public String secureTestApi()
{
    return "Secured AI Api is working!";
}

@PostMapping(value="/upload" , consumes = "multipart/form-data")
public String uploadFile(@RequestParam("file") MultipartFile file){
    try {
            String fileName = file.getOriginalFilename();
            System.out.println("Received file: " + fileName + ", size=" + file.getSize() + ", type=" + file.getContentType());

            String text = pdfService.extractText(file);

            String path = "C:/uploads/" + fileName;

        List<String> chunks= textSplitter.splitText(text);

        for(String chunk: chunks){
 
            List<Double> embedding = embeddingService.getEmbedding(chunk);
            DocumentChunk doc=documentRepository.save(new DocumentChunk(chunk, file.getOriginalFilename()));
            doc.setEmbedding(embedding);
            documentRepository.save(doc);
        }

        return "File uploaded successfully: " + fileName + " at path: " + path + "file stored in DB";
} catch (IllegalArgumentException | IllegalStateException e) {
            e.printStackTrace();
            return "Upload failed: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Upload failed: " + e.getMessage();
    }
}

@GetMapping("/search")
public List<String> search(@RequestParam String query){
    List<DocumentChunk> results= documentRepository.findByContentContainingIgnoreCase(query);
    return results.stream().map(DocumentChunk::getContent).toList();    
}

// @GetMapping("/semantic-search")
// public List<String> semanticSearch(@RequestParam String query){
//     List<Double> queryVector= embeddingService.generateEmbedding(query);

//     return documentRepository.findAll().stream()
//         .filter(doc-> doc.getEmbedding() != null)
//         .sorted((a,b) -> Double.compare(
//            similarityService.cosineSimilarity(queryVector, b.getEmbedding()),
//            similarityService.cosineSimilarity(queryVector, a.getEmbedding())
//         )).limit(3)
//         .map(DocumentChunk::getContent)
//         .toList();

// }

@Autowired
private OllamaChatService chatService;

@GetMapping("/ask")
public String ask(@RequestParam String query) {

    List<Double> queryVector = embeddingService.getEmbedding(query);

    List<String> topChunks = documentRepository.findAll().stream()
            .filter(doc -> doc.getEmbedding() != null)
            .sorted((a, b) -> Double.compare(
                    similarityService.cosineSimilarity(b.getEmbedding(), queryVector),
                    similarityService.cosineSimilarity(a.getEmbedding(), queryVector)
            ))
            .limit(3)
            .map(DocumentChunk::getContent)
            .toList();

    String context = String.join("\n", topChunks);

    String prompt = """
    Answer the question using ONLY the context below.

    Context:
    %s

    Question:
    %s
    """.formatted(context, query);

    return chatService.ask(prompt);
}

@GetMapping("/gemini-test")
public String testGemini() {
    return chatService.ask("Say hello from Gemini");
}


}